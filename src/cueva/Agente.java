/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cueva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author toniborras
 */
public class Agente {

    private HashMap<Integer, HashMap<Integer, CasillaAgente>> cuevaMemoria;
    private Main prog;
    private CasillaAgente casillaActual;
    private Pokemon apariencia;
    private ArrayList<Integer[]> monstruosEncontrados;
    private Stack<CasillaAgente> historial;
    private int id;
    private int flechas;

    private int orientacion;
    private boolean encontrado;

    public Agente(Main p, int id, int orientacion) {
        this.prog = p;
        encontrado = false;
        cuevaMemoria = new HashMap();

        switch (id) {
            case 0:
                casillaActual = new CasillaAgente(p.getVista().getDimension() - 1, 0);
                break;
            case 1:
                casillaActual = new CasillaAgente(0, p.getVista().getDimension() - 1);
                break;
            case 2:
                casillaActual = new CasillaAgente(p.getVista().getDimension() - 1, p.getVista().getDimension() - 1);
                break;
            case 3:
                casillaActual = new CasillaAgente(0, 0);
                break;
        }

        historial = new Stack();
        monstruosEncontrados = new ArrayList();
        this.id = id;
        this.apariencia = new Pokemon(p, id);
        this.apariencia.instanciar(id);
        this.prog.getVista().getCharmander().add(apariencia);
        flechas = 0;
        this.orientacion = orientacion;
        this.prog.getVista().repaint();
    }

    public Main getMain() {
        return this.prog;
    }

    public void setCasillaActual(CasillaAgente ca) {
        this.casillaActual = ca;
        //Hacer el movimiento
    }

    public CasillaAgente getCasillaActual() {
        return this.casillaActual;
    }

    public void addCasilla(CasillaAgente ca) {
        try {
            Main.semaforoCueva.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (cuevaMemoria.get(ca.getX()) == null) {
            HashMap<Integer, CasillaAgente> hs = new HashMap();
            hs.put(ca.getY(), ca);
            cuevaMemoria.put(ca.getX(), hs);
        } else if (cuevaMemoria.get(ca.getY()) == null) {
            cuevaMemoria.get(ca.getX()).put(ca.getY(), ca);
        } else {
            cuevaMemoria.get(ca.getX()).remove(ca.getY());
            cuevaMemoria.get(ca.getX()).put(ca.getY(), ca);
        }
        Main.semaforoCueva.release();
    }

    private void mover(int x, int y) {
        if (!historial.isEmpty() && historial.peek() == this.casillaActual) {
            historial.pop();
        }
        historial.push(this.casillaActual);

        this.casillaActual = new CasillaAgente(x, y);
        this.casillaActual.setNumVisitas(this.casillaActual.getNumVisitas() + 1);
        if (this.casillaActual.getNumVisitas() > 1) {
            while (historial.peek().getX() != x && historial.peek().getY() != y) {
                historial.pop();
            }
        }
        percibirCasilla();
        procesarEstados();
        this.addCasilla(casillaActual);

    }

    private void mover(CasillaAgente c) {
        if (!historial.isEmpty() && historial.peek() == this.casillaActual) {
            historial.pop();
        }
        historial.push(this.casillaActual);
        this.casillaActual = c;
        this.casillaActual.setNumVisitas(this.casillaActual.getNumVisitas() + 1);
        if (this.casillaActual.getNumVisitas() > 1 && historial.contains(this.casillaActual)) {
            while (historial.peek() != this.casillaActual) {
                historial.pop();
            }
        }
        percibirCasilla();
        procesarEstados();
        this.addCasilla(casillaActual);
    }

    public void percibirCasilla() {
        Cueva c = this.prog.getCueva();
        try {
            this.casillaActual.setEstados(c.getCasilla(this.casillaActual.getX(), this.casillaActual.getY()).getEstados());
        } catch (Exception e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
        }

        this.casillaActual.setVerificado(true);
        this.casillaActual.setEstados(Estado.SEGURO);
    }

    public void procesarEstados() {
        ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes(this.orientacion);

        if (this.casillaActual.getEstados().contains(Estado.MONSTRUO)) {
            System.exit(0);
        }
        if (this.casillaActual.getEstados().contains(Estado.VACIO)) {
            cAdyacentes.forEach((c) -> {
                if (!c.getVerificado()) {
                    c.setEstados(Estado.SEGURO);
                    this.addCasilla(c);
                }
            });
        }

        if (this.casillaActual.getEstados().contains(Estado.BRISA) && !this.casillaActual.getEstados().contains(Estado.HEDOR)) {
            cAdyacentes.forEach((c) -> {
                if (!c.getVerificado() && !c.getEstados().contains(Estado.SEGURO)) {
                    if (c.getEstados().contains(Estado.POSIBLEMONSTRUO)) {
                        c.removeEstado(Estado.POSIBLEMONSTRUO);
                    } else {
                        c.setEstados(Estado.POSIBLEPRECIPICIO);
                    }
                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });
        }

        if (this.casillaActual.getEstados().contains(Estado.HEDOR) && !this.casillaActual.getEstados().contains(Estado.BRISA)) {
            cAdyacentes.forEach((c) -> {
                if (!c.getVerificado() && !c.getEstados().contains(Estado.SEGURO)) {
                    if (c.getEstados().contains(Estado.POSIBLEPRECIPICIO)) {
                        c.removeEstado(Estado.POSIBLEPRECIPICIO);
                    } else {
                        c.setEstados(Estado.POSIBLEMONSTRUO);
                    }
                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });

        }

        if (this.casillaActual.getEstados().contains(Estado.HEDOR) && this.casillaActual.getEstados().contains(Estado.BRISA)) {
            cAdyacentes.forEach((c) -> {
                if (!c.getVerificado() && !c.getEstados().contains(Estado.SEGURO)) {
                    c.setEstados(Estado.POSIBLEPRECIPICIO);
                    c.setEstados(Estado.POSIBLEMONSTRUO);
                }
                c.setVerificado(false);
                this.addCasilla(c);
            });
        }
    }

    public void razonar() {
        if (!this.monstruosEncontrados.isEmpty()) {
            int indice = 0;
            for (int i = 0; i < monstruosEncontrados.size(); i++) {
                if ((this.casillaActual.getX() == this.monstruosEncontrados.get(i)[0])
                        || (this.casillaActual.getY() == this.monstruosEncontrados.get(i)[1])) {

                    if (flechas > 0) {
                        this.flechas--;
//                        try {
//                            Main.semaforoCueva.acquire();
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                        if (this.casillaActual.getX() == this.monstruosEncontrados.get(i)[0]) {
                            if (this.casillaActual.getY() > this.monstruosEncontrados.get(i)[1]) {
                                this.prog.getVista().lanzarFlecha(this.id, Direccion.OESTE);
                            } else {
                                this.prog.getVista().lanzarFlecha(this.id, Direccion.ESTE);
                            }
                        } else {
                            if (this.casillaActual.getX() > this.monstruosEncontrados.get(i)[0]) {
                                this.prog.getVista().lanzarFlecha(this.id, Direccion.SUR);
                            } else {
                                this.prog.getVista().lanzarFlecha(this.id, Direccion.NORTE);
                            }
                        }
//                        Main.semaforoCueva.release();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.cuevaMemoria.get(this.monstruosEncontrados.get(i)[0]).get(this.monstruosEncontrados.get(i)[1]).setEstados(Estado.SEGURO);

                        indice = i;
                        this.casillaActual = this.cuevaMemoria.get(this.casillaActual.getX()).get(this.casillaActual.getY());

                    }

                }
            }
            this.monstruosEncontrados.remove(indice);

        } else {

            ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes(this.orientacion);
            for (int i = 0; i < cAdyacentes.size(); i++) {
                if (cAdyacentes.get(i).getVerificado()
                        && cAdyacentes.get(i).getEstados().contains(Estado.HEDOR)) {
                    ArrayList<CasillaAgente> cAdyacentesI = this.getAdyacentes(cAdyacentes.get(i), this.orientacion);
                    int posibles = 0;
                    int indice = 0;
                    for (int j = 0; j < cAdyacentesI.size(); j++) {
                        if (cAdyacentesI.get(j).getEstados().contains(Estado.POSIBLEMONSTRUO)
                                && !cAdyacentesI.get(j).getEstados().contains(Estado.SEGURO)) {
                            posibles++;
                            indice = j;

                        }
                    }
                    if (posibles == 1) {
                        Integer[] monstruo = new Integer[2];
                        monstruo[0] = (Integer) cAdyacentesI.get(indice).getX();
                        monstruo[1] = (Integer) cAdyacentesI.get(indice).getY();
                        this.monstruosEncontrados.add(monstruo);

                    }
                } // 
            }
            int posibles = 0;
            for (int i = 0; i < cAdyacentes.size(); i++) {
                if (cAdyacentes.get(i).getEstados().contains(Estado.HEDOR) && cAdyacentes.get(i).getEstados().contains(Estado.MEMORIZADA)) {
                    posibles++;

                }
            }

            if (posibles == cAdyacentes.size()) {

                lanzarFlecha();
                cAdyacentes = this.getAdyacentes(this.orientacion);

            } else if (this.casillaActual.getEstados().contains(Estado.TESORO)) {
                this.prog.getController().get(id).quitarTesoro(this.casillaActual);

                try {
                    Main.semaforoTesoros.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.prog.quitarTesoro();
                Main.semaforoTesoros.release();
                this.encontrado = true;
            } else if (this.casillaActual.getEstados().contains(Estado.HEDOR)
                    || this.casillaActual.getEstados().contains(Estado.BRISA)) {
                ArrayList<CasillaAgente> noVerificadasSeguras = (ArrayList<CasillaAgente>) cAdyacentes.clone();
                noVerificadasSeguras.removeIf(c -> c.getVerificado() || !c.getEstados().contains(Estado.SEGURO));

                if (!noVerificadasSeguras.isEmpty()) {

                    this.apariencia.moverPokemon(this.getDireccion(noVerificadasSeguras.get(0).getX(), noVerificadasSeguras.get(0).getY()));
                    mover(noVerificadasSeguras.get(0).getX(), noVerificadasSeguras.get(0).getY());

                } else {
                    int menosVisto = 0;
                    int comp = 10000;
                    for (int i = 0; i < cAdyacentes.size(); i++) {
                        if (cAdyacentes.get(i).getEstados().contains(Estado.SEGURO)) {

                            if (comp > cAdyacentes.get(i).getNumVisitas()) {
                                comp = cAdyacentes.get(i).getNumVisitas();
                                menosVisto = i;
                            }
                        }
                    }

                    this.apariencia.moverPokemon(this.getDireccion(cAdyacentes.get(menosVisto).getX(), cAdyacentes.get(menosVisto).getY()));
                    mover(cAdyacentes.get(menosVisto));

                }
            } else { //NO HAY NADA PELIGROSO PERCEPCION
                ArrayList<CasillaAgente> noVerificadas = (ArrayList<CasillaAgente>) cAdyacentes.clone();
                noVerificadas.removeIf(c -> c.getVerificado()
                        || c.getX() >= this.prog.getCueva().getTamanyo() || c.getX() < 0
                        || c.getY() >= this.prog.getCueva().getTamanyo() || c.getY() < 0);

                boolean yes = false;
                while (!yes) {
                    if (!noVerificadas.isEmpty()) {

                        if (noVerificadas.get(0).getEstados().contains(Estado.SEGURO)) {
                            boolean seguir = true;

                            try {
                                this.apariencia.moverPokemon(this.getDireccion(noVerificadas.get(0).getX(), noVerificadas.get(0).getY()));
                                mover(noVerificadas.get(0).getX(), noVerificadas.get(0).getY());
                            } catch (Exception ex) {
                                if (!noVerificadas.isEmpty()) {
                                    while (seguir) {
                                        if (!noVerificadas.isEmpty()) {
                                            if (noVerificadas.get(0).getEstados().contains(Estado.SEGURO)) {

                                                switch (this.getDireccion(noVerificadas.get(0).getX(), noVerificadas.get(0).getY())) {
                                                    case NORTE:
                                                        this.casillaActual.setEstados(Estado.GOLPENORTE);
                                                        this.addCasilla(casillaActual);
                                                        break;
                                                    case SUR:
                                                        this.casillaActual.setEstados(Estado.GOLPESUR);
                                                        this.addCasilla(casillaActual);
                                                        break;
                                                    case ESTE:
                                                        this.casillaActual.setEstados(Estado.GOLPEESTE);
                                                        this.addCasilla(casillaActual);
                                                        break;
                                                    case OESTE:
                                                        this.casillaActual.setEstados(Estado.GOLPEOESTE);
                                                        this.addCasilla(casillaActual);
                                                        break;
                                                }
                                                noVerificadas.remove(0);
                                                try {
                                                    if (!noVerificadas.isEmpty()) {

                                                        this.apariencia.moverPokemon(this.getDireccion(noVerificadas.get(0).getX(), noVerificadas.get(0).getY()));
                                                        mover(noVerificadas.get(0).getX(), noVerificadas.get(0).getY());
                                                        seguir = false;
                                                    } else {
                                                        CasillaAgente cAnterior = this.historial.pop();
                                                        mover(cAnterior);
                                                    }

                                                } catch (Exception e) {
                                                    seguir = true;
                                                }
                                            }
                                        } else {
                                            CasillaAgente cAnterior = this.historial.pop();
                                            mover(cAnterior);
                                            this.apariencia.moverPokemon(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
                                            seguir = false;
                                        }
                                    }

                                } else {
                                    CasillaAgente cAnterior = this.historial.pop();
                                    mover(cAnterior);
                                    this.apariencia.moverPokemon(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
                                }

                            }
                            yes = true;

                        } else {
                            noVerificadas.remove(0);
                        }

                    } else {

                        yes = true;
                        CasillaAgente c = getMinimumVisits(cAdyacentes);
                        try {
                            this.apariencia.moverPokemon(this.getDireccion(c.getX(), c.getY()));
                        } catch (Exception ex) {

                        }
                        mover(c);
                    }
                }
            }
        }
    }

    private CasillaAgente getAdyacenteEste() {
        try {
            Main.semaforoCueva.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (this.cuevaMemoria.get(casillaActual.getX()) != null && this.cuevaMemoria.get(casillaActual.getX()).containsKey(casillaActual.getY() + 1)) {
            CasillaAgente g = new CasillaAgente(0, 0);
            Cueva cueva = this.prog.getCueva();
            if (this.cuevaMemoria.get(casillaActual.getX()).get(
                    casillaActual.getY() + 1).getVerificado()) {
                g.setEstados(cueva.getCasilla(this.casillaActual.getX(), this.casillaActual.getY() + 1).getEstados());
                for (int i = 0; i < g.getEstados().size(); i++) {
                    if (!this.cuevaMemoria.get(casillaActual.getX()).get(
                            casillaActual.getY() + 1).getEstados().contains(g.getEstados().get(i))) {
                        this.cuevaMemoria.get(casillaActual.getX()).get(
                                casillaActual.getY() + 1).setEstados(g.getEstados().get(i));
                    }

                }
            }

            if (!casillaActual.getEstados().contains(Estado.HEDOR)
                    && !this.casillaActual.getEstados().contains(Estado.BRISA)) {
                this.cuevaMemoria.get(casillaActual.getX()).get(
                        casillaActual.getY() + 1).setEstados(Estado.SEGURO);

            }

            this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() + 1).setEstados(Estado.MEMORIZADA);
//            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() + 1));
            CasillaAgente c = this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() + 1);
            Main.semaforoCueva.release();
            return c;
        } else {
            if (this.casillaActual.getY() + 1 < this.prog.getCueva().getTamanyo()) {
                CasillaAgente ce = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() + 1);
                Main.semaforoCueva.release();
//                cAdyacentes.add(ce);
                ce.setVerificado(false);
                return ce;
            }
        }
        Main.semaforoCueva.release();
        return null;
    }

    private CasillaAgente getAdyacenteEste(CasillaAgente a) {
        try {
            Main.semaforoCueva.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.cuevaMemoria.get(a.getX()) != null && this.cuevaMemoria.get(a.getX()).containsKey(a.getY() + 1)) {
            CasillaAgente g = new CasillaAgente(0, 0);
            Cueva cueva = this.prog.getCueva();
            g.setEstados(cueva.getCasilla(a.getX(), a.getY() + 1).getEstados());
            if (this.cuevaMemoria.get(a.getX()).get(
                    a.getY() + 1).getVerificado()) {
                for (int i = 0; i < g.getEstados().size(); i++) {
                    if (!this.cuevaMemoria.get(a.getX()).get(
                            a.getY() + 1).getEstados().contains(g.getEstados().get(i))) {
                        this.cuevaMemoria.get(a.getX()).get(
                                a.getY() + 1).setEstados(g.getEstados().get(i));
                    }

                }
            }
            if (!a.getEstados().contains(Estado.HEDOR)
                    && !a.getEstados().contains(Estado.BRISA)) {
                this.cuevaMemoria.get(a.getX()).get(
                        a.getY() + 1).setEstados(Estado.SEGURO);

            }
            this.cuevaMemoria.get(a.getX()).get(a.getY() + 1).setEstados(Estado.MEMORIZADA);
//            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() + 1));
            CasillaAgente c = this.cuevaMemoria.get(a.getX()).get(a.getY() + 1);
            Main.semaforoCueva.release();
            return c;
        } else {
            if (a.getY() + 1 < this.prog.getCueva().getTamanyo()) {
                CasillaAgente ce = new CasillaAgente(a.getX(), a.getY() + 1);
                Main.semaforoCueva.release();
                ce.setVerificado(false);
//                cAdyacentes.add(ce);
                return ce;
            }
        }
        Main.semaforoCueva.release();
        return null;
    }

    private CasillaAgente getAdyacenteOeste() {
        try {
            Main.semaforoCueva.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.cuevaMemoria.get(casillaActual.getX()) != null && this.cuevaMemoria.get(casillaActual.getX()).containsKey(casillaActual.getY() - 1)) {
            CasillaAgente g = new CasillaAgente(0, 0);
            Cueva cueva = this.prog.getCueva();
            g.setEstados(cueva.getCasilla(this.casillaActual.getX(), this.casillaActual.getY() - 1).getEstados());
            if (this.cuevaMemoria.get(casillaActual.getX()).get(
                    casillaActual.getY() - 1).getVerificado()) {
                for (int i = 0; i < g.getEstados().size(); i++) {
                    if (!this.cuevaMemoria.get(casillaActual.getX()).get(
                            casillaActual.getY() - 1).getEstados().contains(g.getEstados().get(i))) {
                        this.cuevaMemoria.get(casillaActual.getX()).get(
                                casillaActual.getY() - 1).setEstados(g.getEstados().get(i));
                    }

                }
            }
            if (!casillaActual.getEstados().contains(Estado.HEDOR)
                    && !this.casillaActual.getEstados().contains(Estado.BRISA)) {
                this.cuevaMemoria.get(casillaActual.getX()).get(
                        casillaActual.getY() - 1).setEstados(Estado.SEGURO);

            }
            this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() - 1).setEstados(Estado.MEMORIZADA);
//            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() - 1));
            CasillaAgente c = this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() - 1);
            Main.semaforoCueva.release();
            return c;
        } else {
            if (this.casillaActual.getY() - 1 >= 0) {
                CasillaAgente cw = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() - 1);
                Main.semaforoCueva.release();
//                cAdyacentes.add(cw);
                cw.setVerificado(false);
                return cw;

            }
        }
        Main.semaforoCueva.release();
        return null;
    }

    private CasillaAgente getAdyacenteOeste(CasillaAgente a) {
        try {
            Main.semaforoCueva.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.cuevaMemoria.get(a.getX()) != null && this.cuevaMemoria.get(a.getX()).containsKey(a.getY() - 1)) {
            CasillaAgente g = new CasillaAgente(0, 0);
            Cueva cueva = this.prog.getCueva();
            g.setEstados(cueva.getCasilla(a.getX(), a.getY() - 1).getEstados());
            if (this.cuevaMemoria.get(a.getX()).get(
                    a.getY() - 1).getVerificado()) {
                for (int i = 0; i < g.getEstados().size(); i++) {
                    if (!this.cuevaMemoria.get(a.getX()).get(
                            a.getY() - 1).getEstados().contains(g.getEstados().get(i))) {
                        this.cuevaMemoria.get(a.getX()).get(
                                a.getY() - 1).setEstados(g.getEstados().get(i));
                    }

                }
            }
            if (!a.getEstados().contains(Estado.HEDOR)
                    && !a.getEstados().contains(Estado.BRISA)) {
                this.cuevaMemoria.get(a.getX()).get(
                        a.getY() - 1).setEstados(Estado.SEGURO);

            }
            this.cuevaMemoria.get(a.getX()).get(a.getY() - 1).setEstados(Estado.MEMORIZADA);
//            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() - 1));
            CasillaAgente c = this.cuevaMemoria.get(a.getX()).get(a.getY() - 1);
            Main.semaforoCueva.release();
            return c;
        } else {
            if (a.getY() - 1 >= 0) {
                CasillaAgente cw = new CasillaAgente(a.getX(), a.getY() - 1);
                Main.semaforoCueva.release();
//                cAdyacentes.add(cw);
                cw.setVerificado(false);
                return cw;
            }
        }
        Main.semaforoCueva.release();
        return null;
    }

    private CasillaAgente getAdyacenteNorte() {
        try {
            Main.semaforoCueva.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.cuevaMemoria.containsKey(casillaActual.getX() - 1)) {
            if (this.cuevaMemoria.get(casillaActual.getX() - 1).containsKey(casillaActual.getY())) {
                CasillaAgente g = new CasillaAgente(0, 0);
                Cueva cueva = this.prog.getCueva();
                g.setEstados(cueva.getCasilla(this.casillaActual.getX() - 1, this.casillaActual.getY()).getEstados());
                if (this.cuevaMemoria.get(casillaActual.getX() - 1).get(
                        casillaActual.getY()).getVerificado()) {
                    for (int i = 0; i < g.getEstados().size(); i++) {
                        if (!this.cuevaMemoria.get(casillaActual.getX() - 1).get(
                                casillaActual.getY()).getEstados().contains(g.getEstados().get(i))) {
                            this.cuevaMemoria.get(casillaActual.getX() - 1).get(
                                    casillaActual.getY()).setEstados(g.getEstados().get(i));
                        }

                    }
                }
                if (!casillaActual.getEstados().contains(Estado.HEDOR)
                        && !this.casillaActual.getEstados().contains(Estado.BRISA)) {
                    this.cuevaMemoria.get(casillaActual.getX() - 1).get(
                            casillaActual.getY()).setEstados(Estado.SEGURO);

                }
                this.cuevaMemoria.get(casillaActual.getX() - 1).get(casillaActual.getY()).setEstados(Estado.MEMORIZADA);
//                cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() - 1).get(casillaActual.getY()));
                CasillaAgente c = this.cuevaMemoria.get(casillaActual.getX() - 1).get(casillaActual.getY());
                Main.semaforoCueva.release();
                return c;
            } else {
                if (this.casillaActual.getX() - 1 >= 0) {
                    CasillaAgente cn = new CasillaAgente(this.casillaActual.getX() - 1, this.casillaActual.getY());
                    Main.semaforoCueva.release();
//                    cAdyacentes.add(cn);
                    return cn;
                }
            }
        } else {
            if (this.casillaActual.getX() - 1 >= 0) {
                CasillaAgente cn = new CasillaAgente(this.casillaActual.getX() - 1, this.casillaActual.getY());
                Main.semaforoCueva.release();
//                cAdyacentes.add(cn);
                cn.setVerificado(false);
                return cn;
            }
        }
        Main.semaforoCueva.release();
        return null;
    }

    private CasillaAgente getAdyacenteNorte(CasillaAgente a) {
        try {
            Main.semaforoCueva.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (this.cuevaMemoria.containsKey(a.getX() - 1)) {
            if (this.cuevaMemoria.get(a.getX() - 1).containsKey(a.getY())) {
                CasillaAgente g = new CasillaAgente(0, 0);
                Cueva cueva = this.prog.getCueva();
                g.setEstados(cueva.getCasilla(a.getX() - 1, a.getY()).getEstados());
                if (this.cuevaMemoria.get(a.getX() - 1).get(
                        a.getY()).getVerificado()) {
                    for (int i = 0; i < g.getEstados().size(); i++) {
                        if (!this.cuevaMemoria.get(a.getX() - 1).get(
                                a.getY()).getEstados().contains(g.getEstados().get(i))) {
                            this.cuevaMemoria.get(a.getX() - 1).get(
                                    a.getY()).setEstados(g.getEstados().get(i));
                        }

                    }
                }
                if (!a.getEstados().contains(Estado.HEDOR)
                        && !a.getEstados().contains(Estado.BRISA)) {
                    this.cuevaMemoria.get(a.getX() - 1).get(
                            a.getY()).setEstados(Estado.SEGURO);

                }
                this.cuevaMemoria.get(a.getX() - 1).get(a.getY()).setEstados(Estado.MEMORIZADA);
//                cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() - 1).get(casillaActual.getY()));
                CasillaAgente c = this.cuevaMemoria.get(a.getX() - 1).get(a.getY());
                Main.semaforoCueva.release();
                return c;
            } else {
                if (a.getX() - 1 >= 0) {
                    CasillaAgente cn = new CasillaAgente(a.getX() - 1, a.getY());
                    Main.semaforoCueva.release();
//                    cAdyacentes.add(cn);
                    return cn;
                }
            }
        } else {
            if (a.getX() - 1 >= 0) {
                CasillaAgente cn = new CasillaAgente(a.getX() - 1, a.getY());
                Main.semaforoCueva.release();
//                cAdyacentes.add(cn);
                cn.setVerificado(false);
                return cn;
            }
        }
        Main.semaforoCueva.release();
        return null;
    }

    private CasillaAgente getAdyacenteSur() {
        try {
            Main.semaforoCueva.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.cuevaMemoria.containsKey(casillaActual.getX() + 1)) {
            if (this.cuevaMemoria.get(casillaActual.getX() + 1).containsKey(casillaActual.getY())) {
                CasillaAgente g = new CasillaAgente(0, 0);
                Cueva cueva = this.prog.getCueva();
                g.setEstados(cueva.getCasilla(this.casillaActual.getX() + 1, this.casillaActual.getY()).getEstados());
                if (this.cuevaMemoria.get(casillaActual.getX() + 1).get(
                        casillaActual.getY()).getVerificado()) {
                    for (int i = 0; i < g.getEstados().size(); i++) {
                        if (!this.cuevaMemoria.get(casillaActual.getX() + 1).get(
                                casillaActual.getY()).getEstados().contains(g.getEstados().get(i))) {
                            this.cuevaMemoria.get(casillaActual.getX() + 1).get(
                                    casillaActual.getY()).setEstados(g.getEstados().get(i));
                        }

                    }
                }
                if (!casillaActual.getEstados().contains(Estado.HEDOR)
                        && !this.casillaActual.getEstados().contains(Estado.BRISA)) {
                    this.cuevaMemoria.get(casillaActual.getX() + 1).get(
                            casillaActual.getY()).setEstados(Estado.SEGURO);

                }
                this.cuevaMemoria.get(casillaActual.getX() + 1).get(casillaActual.getY()).setEstados(Estado.MEMORIZADA);
                CasillaAgente c = this.cuevaMemoria.get(casillaActual.getX() + 1).get(casillaActual.getY());
                Main.semaforoCueva.release();
                return c;

//                cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() + 1).get(casillaActual.getY()));
            } else {
                if (this.casillaActual.getX() + 1 < this.prog.getCueva().getTamanyo()) {
                    CasillaAgente cs = new CasillaAgente(this.casillaActual.getX() + 1, this.casillaActual.getY());
                    Main.semaforoCueva.release();
//                    cAdyacentes.add(cs);
                    return cs;
                }
            }
        } else {
            if (this.casillaActual.getX() + 1 < this.prog.getCueva().getTamanyo()) {

                CasillaAgente cs = new CasillaAgente(this.casillaActual.getX() + 1, this.casillaActual.getY());
//                cAdyacentes.add(cs);
                Main.semaforoCueva.release();
                cs.setVerificado(false);
                return cs;
            }
        }
        Main.semaforoCueva.release();
        return null;
    }

    private CasillaAgente getAdyacenteSur(CasillaAgente a) {
        try {
            Main.semaforoCueva.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.cuevaMemoria.containsKey(a.getX() + 1)) {
            if (this.cuevaMemoria.get(a.getX() + 1).containsKey(a.getY())) {
                CasillaAgente g = new CasillaAgente(0, 0);
                Cueva cueva = this.prog.getCueva();
                g.setEstados(cueva.getCasilla(a.getX() + 1, a.getY()).getEstados());
                if (this.cuevaMemoria.get(a.getX() + 1).get(
                        a.getY()).getVerificado()) {
                    for (int i = 0; i < g.getEstados().size(); i++) {
                        if (!this.cuevaMemoria.get(a.getX() + 1).get(
                                a.getY()).getEstados().contains(g.getEstados().get(i))) {
                            this.cuevaMemoria.get(a.getX() + 1).get(
                                    a.getY()).setEstados(g.getEstados().get(i));
                        }

                    }
                }
                if (!a.getEstados().contains(Estado.HEDOR)
                        && !a.getEstados().contains(Estado.BRISA)) {
                    this.cuevaMemoria.get(a.getX() + 1).get(
                            a.getY()).setEstados(Estado.SEGURO);

                }
                this.cuevaMemoria.get(a.getX() + 1).get(a.getY()).setEstados(Estado.MEMORIZADA);
                CasillaAgente c = this.cuevaMemoria.get(a.getX() + 1).get(a.getY());
                Main.semaforoCueva.release();
                return c;

//                cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() + 1).get(casillaActual.getY()));
            } else {
                if (a.getX() + 1 < this.prog.getCueva().getTamanyo()) {
                    CasillaAgente cs = new CasillaAgente(a.getX() + 1, a.getY());
                    Main.semaforoCueva.release();
//                    cAdyacentes.add(cs);
                    return cs;
                }
            }
        } else {
            if (a.getX() + 1 < this.prog.getCueva().getTamanyo()) {

                CasillaAgente cs = new CasillaAgente(a.getX() + 1, a.getY());
//                cAdyacentes.add(cs);
                Main.semaforoCueva.release();
                cs.setVerificado(false);
                return cs;
            }
        }
        Main.semaforoCueva.release();
        return null;
    }

    private ArrayList<CasillaAgente> getAdyacentes(int orientacion) {
        ArrayList<CasillaAgente> cAdyacentes = new ArrayList<>();
        CasillaAgente c;
        switch (orientacion) {
            case 0:
                c = this.getAdyacenteEste();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteSur();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteOeste();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = getAdyacenteNorte();
                if (c != null) {
                    cAdyacentes.add(c);
                }

                break;
            case 1:
                c = this.getAdyacenteOeste();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = getAdyacenteNorte();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteEste();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteSur();
                if (c != null) {
                    cAdyacentes.add(c);
                }

                break;
            case 2:
                c = getAdyacenteNorte();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteEste();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteSur();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteOeste();
                if (c != null) {
                    cAdyacentes.add(c);
                }

                break;
            case 3:
                c = this.getAdyacenteSur();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteOeste();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = getAdyacenteNorte();
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteEste();
                if (c != null) {
                    cAdyacentes.add(c);
                }

                break;
        }

        return cAdyacentes;
    }

    private ArrayList<CasillaAgente> getAdyacentes(CasillaAgente a, int orientacion) {
        ArrayList<CasillaAgente> cAdyacentes = new ArrayList<>();
        CasillaAgente c;
        switch (orientacion) {
            case 0:
                c = this.getAdyacenteEste(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteSur(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteOeste(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = getAdyacenteNorte(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }

                break;
            case 1:
                c = this.getAdyacenteOeste(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = getAdyacenteNorte(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteEste(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteSur(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }

                break;
            case 2:
                c = getAdyacenteNorte(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteEste(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteSur(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteOeste(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }

                break;
            case 3:
                c = this.getAdyacenteSur(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteOeste(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = getAdyacenteNorte(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }
                c = this.getAdyacenteEste(a);
                if (c != null) {
                    cAdyacentes.add(c);
                }

                break;
        }

        return cAdyacentes;
    }

    public void volver() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
        CasillaAgente cAnterior = this.historial.pop();
        this.apariencia.moverPokemon(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
        this.casillaActual = cAnterior;
    }

    public boolean isSalida() {
        return this.casillaActual.getX() == this.prog.getVista().getDimension() - 1
                && this.casillaActual.getY() == 0;
    }

    public boolean isSalida(int id) {
        switch (id) {
            case 0:
                return this.casillaActual.getX() == this.prog.getVista().getDimension() - 1
                        && this.casillaActual.getY() == 0;

            case 1:
                return this.casillaActual.getX() == 0
                        && this.casillaActual.getY() == this.prog.getVista().getDimension() - 1;

            case 2:
                return this.casillaActual.getX() == this.prog.getVista().getDimension() - 1
                        && this.casillaActual.getY() == this.prog.getVista().getDimension() - 1;

            case 3:
                return this.casillaActual.getX() == 0
                        && this.casillaActual.getY() == 0;

        }
        return false;

    }

    public void setCharmander(Pokemon m) {
        this.apariencia = m;
    }

    public Pokemon getCharmander() {
        return this.apariencia;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;

    }

    private Direccion getDireccion(int x, int y) {
        if (this.casillaActual.getX() < x) {
            return Direccion.SUR;
        }
        if (this.casillaActual.getX() > x) {
            return Direccion.NORTE;
        }
        if (this.casillaActual.getY() < y) {
            return Direccion.ESTE;
        }
        return Direccion.OESTE;
    }

    public CasillaAgente getMinimumVisits(ArrayList<CasillaAgente> list) {
        CasillaAgente c = null;
        for (int i = 0; i < list.size(); i++) {
            if (c == null || list.get(i).getNumVisitas() < c.getNumVisitas()) {
                c = list.get(i);
            }
        }
        return c;
    }

    public int getFlechas() {
        return flechas;
    }

    public void setFlechas(int flechas) {
        this.flechas = flechas;
    }

    private void lanzarFlecha() {
        if (this.flechas > 0) {
            Random r = new Random();
            ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes(this.orientacion);
//            int decision = r.nextInt(cAdyacentes.size());
//            this.prog.getVista().lanzarFlecha(id, Direccion.NORTE);
            this.flechas--;
        }

    }

}
