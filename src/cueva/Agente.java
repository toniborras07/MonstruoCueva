/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cueva;

import java.util.ArrayList;
import java.util.HashMap;
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
    private Charmander apariencia;

    private Stack<CasillaAgente> historial;

    private boolean encontrado;

    public Agente(Main p) {
        this.prog = p;
        encontrado = false;
        cuevaMemoria = new HashMap();
        casillaActual = new CasillaAgente(0, 0);
        historial = new Stack();

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
//        ca.setVerificado(true);
    }

    private void mover(int x, int y) {
        if(!historial.isEmpty() && historial.peek() == this.casillaActual) {
            historial.pop();
        }
        historial.push(this.casillaActual);
        
        this.casillaActual = new CasillaAgente(x, y);
        System.out.println("x: " + x + " y: " + y);
        this.casillaActual.setNumVisitas(this.casillaActual.getNumVisitas() + 1);
        if(this.casillaActual.getNumVisitas() > 1) {
            while(historial.peek().getX() != x && historial.peek().getY() != y) {
                historial.pop();
            }
        }
        percibirCasilla();
        procesarEstados();
        this.addCasilla(casillaActual);

    }

    private void mover(CasillaAgente c) {
        if(!historial.isEmpty() && historial.peek() == this.casillaActual) {
            historial.pop();
        }
        historial.push(this.casillaActual);
        this.casillaActual = c;
        System.out.println("x: " + c.getX() + " y: " + c.getY());
        this.casillaActual.setNumVisitas(this.casillaActual.getNumVisitas() + 1);
        if(this.casillaActual.getNumVisitas() > 1 && historial.contains(this.casillaActual)) {
            while(historial.peek() != this.casillaActual) {
                historial.pop();
            }
        }
        percibirCasilla();
        procesarEstados();
        this.addCasilla(casillaActual);

    }

//    private void mover() {
//        historial.push(this.casillaActual);
//        ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes();
//        boolean seguir = true;
//        for (int i = 0; i < cAdyacentes.size() && seguir; i++) {
//            if ((cAdyacentes.get(i).getEstados().contains(Estado.VACIO)
//                    || cAdyacentes.get(i).getEstados().contains(Estado.SEGURO)) && !cAdyacentes.get(i).getVerificado()) {
//                seguir = false;
//                this.casillaActual = new CasillaAgente(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY());
//                percibirCasilla();
//                procesarEstados();
//                this.addCasilla(casillaActual);
//            }
//        }
//
//    }
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
        ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes();

        if (this.casillaActual.getEstados().contains(Estado.VACIO)) {
            cAdyacentes.forEach((c) -> {
                if (!c.getVerificado()) {
                    c.setEstados(Estado.SEGURO);
//                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });
        }

        if (this.casillaActual.getEstados().contains(Estado.BRISA) && !this.casillaActual.getEstados().contains(Estado.HEDOR)) {
            this.casillaActual.setEstados(Estado.SEGURO);
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
            this.casillaActual.setEstados(Estado.SEGURO);
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
            this.casillaActual.setEstados(Estado.SEGURO);
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

    public void razonar() throws InterruptedException {
        ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes();
        if (this.casillaActual.getEstados().contains(Estado.TESORO)) {
            this.prog.getController().quitarTesoro(this.casillaActual);
            this.encontrado = true;
        } //            }
        else if (this.casillaActual.getEstados().contains(Estado.HEDOR)
                || this.casillaActual.getEstados().contains(Estado.BRISA)) {
            ArrayList<CasillaAgente> noVerificadasSeguras = (ArrayList<CasillaAgente>) cAdyacentes.clone();
            noVerificadasSeguras.removeIf(c -> c.getVerificado() || !c.getEstados().contains(Estado.SEGURO));

            if (!noVerificadasSeguras.isEmpty()) {
//                noVerificadasSeguras.forEach((c) -> {
                this.prog.getVista().moverCharmander(this.getDireccion(noVerificadasSeguras.get(0).getX(), noVerificadasSeguras.get(0).getY()));
                mover(noVerificadasSeguras.get(0).getX(), noVerificadasSeguras.get(0).getY());
//                });
            } else {
//                cAdyacentes.forEach((c) -> {
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

                this.prog.getVista().moverCharmander(this.getDireccion(cAdyacentes.get(menosVisto).getX(), cAdyacentes.get(menosVisto).getY()));
                mover(cAdyacentes.get(menosVisto));

//                });
            }
        } //        if (this.casillaActual.getEstados().contains(Estado.HEDOR)) {
        //            for (int i = 0; i < cAdyacentes.size(); i++) {
        //                if (!cAdyacentes.get(i).getEstados().contains(Estado.POSIBLEMONSTRUO)) {
        ////                        cAdyacentes.get(i).setEstados(Estado.MONSTRUO);
        //                    if (!cAdyacentes.get(i).getEstados().contains(Estado.POSIBLEPRECIPICIO)) {
        //                        if (!cAdyacentes.get(i).getVerificado()) {
        //                            this.prog.getVista().moverCharmander(this.getDireccion(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY()));
        //                            this.mover(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY());
        //
        //                        } else {
        //                            CasillaAgente cAnterior = this.historial.pop();
        //
        //                            this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
        //                            this.casillaActual = cAnterior;
        //                        }
        //                    } else {
        //                        CasillaAgente cAnterior = this.historial.pop();
        //
        //                        this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
        //                        this.casillaActual = cAnterior;
        //                    }
        //
        //                } else {
        //                    CasillaAgente cAnterior = this.historial.pop();
        //
        //                    this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
        //                    this.casillaActual = cAnterior;
        //                }
        //
        //            }
        //        } 
        else { //NO HAY NADA PELIGROSO PERCEPCION
            ArrayList<CasillaAgente> noVerificadas = (ArrayList<CasillaAgente>) cAdyacentes.clone();
            noVerificadas.removeIf(c -> c.getVerificado()
                    || c.getX() >= this.prog.getCueva().getTamanyo() || c.getX() < 0
                    || c.getY() >= this.prog.getCueva().getTamanyo() || c.getY() < 0);

            if (!noVerificadas.isEmpty()) {
//                noVerificadas.forEach((c) -> {
                if (noVerificadas.get(0).getEstados().contains(Estado.SEGURO)) {
                    boolean seguir = true;

                    try {
                        this.prog.getVista().moverCharmander(this.getDireccion(noVerificadas.get(0).getX(), noVerificadas.get(0).getY()));
                    } catch (Exception ex) {
                        while (seguir) {
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
                                    this.prog.getVista().moverCharmander(this.getDireccion(noVerificadas.get(0).getX(), noVerificadas.get(0).getY()));
                                    seguir = false;
//                                    mover(noVerificadas.get(0).getX(), noVerificadas.get(0).getY());
                                } else {
                                    CasillaAgente cAnterior = this.historial.pop();
                                    mover(cAnterior);
                                }

                            } catch (Exception e) {
                                seguir = true;
                            }
                        }

                    }
                    mover(noVerificadas.get(0).getX(), noVerificadas.get(0).getY());

                }
//                });
            } else {

                CasillaAgente c = getMinimumVisits(cAdyacentes);
                try {
                    this.prog.getVista().moverCharmander(this.getDireccion(c.getX(), c.getY()));
                } catch (Exception ex) {

                }
                mover(c);

            }
        }
    }

    private ArrayList<CasillaAgente> getAdyacentes() {
        ArrayList<CasillaAgente> cAdyacentes = new ArrayList<>();
        if (this.cuevaMemoria.get(casillaActual.getX()).containsKey(casillaActual.getY() + 1)) {
            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() + 1));
        } else {
            if (this.casillaActual.getY() + 1 < this.prog.getCueva().getTamanyo()) {
                CasillaAgente ce = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() + 1);
                cAdyacentes.add(ce);
            }
        }

        if (this.cuevaMemoria.containsKey(casillaActual.getX() + 1)) {
            if (this.cuevaMemoria.get(casillaActual.getX() + 1).containsKey(casillaActual.getY())) {
                cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() + 1).get(casillaActual.getY()));
            } else {
                if (this.casillaActual.getX() + 1 < this.prog.getCueva().getTamanyo()) {
                    CasillaAgente cs = new CasillaAgente(this.casillaActual.getX() + 1, this.casillaActual.getY());
                    cAdyacentes.add(cs);
                }
            }
        } else {
            if (this.casillaActual.getX() + 1 < this.prog.getCueva().getTamanyo()) {
                CasillaAgente cs = new CasillaAgente(this.casillaActual.getX() + 1, this.casillaActual.getY());
                cAdyacentes.add(cs);
            }
        }

        if (this.cuevaMemoria.get(casillaActual.getX()).containsKey(casillaActual.getY() - 1)) {
            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() - 1));
        } else {
            if (this.casillaActual.getY() - 1 >= 0) {
                CasillaAgente cw = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() - 1);
                cAdyacentes.add(cw);
            }
        }

        if (this.cuevaMemoria.containsKey(casillaActual.getX() - 1)) {
            if (this.cuevaMemoria.get(casillaActual.getX() - 1).containsKey(casillaActual.getY())) {
                cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() - 1).get(casillaActual.getY()));
            } else {
                if (this.casillaActual.getX() - 1 >= 0) {
                    CasillaAgente cn = new CasillaAgente(this.casillaActual.getX() - 1, this.casillaActual.getY());
                    cAdyacentes.add(cn);
                }
            }
        } else {
            if (this.casillaActual.getX() - 1 >= 0) {
                CasillaAgente cn = new CasillaAgente(this.casillaActual.getX() - 1, this.casillaActual.getY());
                cAdyacentes.add(cn);
            }
        }

        return cAdyacentes;
    }

    public void volver() {
        CasillaAgente cAnterior = this.historial.pop();
        this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
        this.casillaActual = cAnterior;
    }

    public boolean isSalida() {
        return this.casillaActual.getX() == 0 && this.casillaActual.getY() == 0;
    }

    public void setCharmander(Charmander m) {
        this.apariencia = m;
    }

    public Charmander getCharmander() {
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

}
