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
        historial.push(this.casillaActual);
        this.casillaActual = new CasillaAgente(x, y);
        System.out.println("x: " + x + " y: " + y);
        this.casillaActual.setNumVisitas(this.casillaActual.getNumVisitas() + 1);
        percibirCasilla();
        procesarEstados();
        this.addCasilla(casillaActual);

    }

    private void mover(CasillaAgente c) {
        historial.push(this.casillaActual);
        this.casillaActual = c;
        this.casillaActual.setNumVisitas(this.casillaActual.getNumVisitas() + 1);
        percibirCasilla();
        procesarEstados();
        this.addCasilla(casillaActual);

    }

    private void mover() {
        historial.push(this.casillaActual);
        ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes();
        boolean seguir = true;
        for (int i = 0; i < cAdyacentes.size() && seguir; i++) {
            if ((cAdyacentes.get(i).getEstados().contains(Estado.VACIO)
                    || cAdyacentes.get(i).getEstados().contains(Estado.SEGURO)) && !cAdyacentes.get(i).getVerificado()) {
                seguir = false;
                this.casillaActual = new CasillaAgente(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY());
                percibirCasilla();
                procesarEstados();
                this.addCasilla(casillaActual);
            }
        }

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
                        c.setEstados(Estado.SEGURO);
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
                if (!c.getVerificado()) {
                    if (c.getEstados().contains(Estado.POSIBLEPRECIPICIO)) {
                        c.removeEstado(Estado.POSIBLEPRECIPICIO);
                        c.setEstados(Estado.SEGURO);
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

        if (this.casillaActual.getEstados().contains(Estado.BRILLANTE)) {
            this.casillaActual.setEstados(Estado.SEGURO);
            cAdyacentes.forEach((c) -> {
                if (!c.getVerificado()) {
                    if (!(this.casillaActual.getEstados().contains(Estado.HEDOR) || this.casillaActual.getEstados().contains(Estado.BRISA))) {
                        c.setEstados(Estado.SEGURO);
                    }
                    c.setEstados(Estado.POSIBLETESORO);
                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });
        }
    }

    public void razonar() throws InterruptedException {
        ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes();

        if (this.casillaActual.getEstados().contains(Estado.HEDOR) || this.casillaActual.getEstados().contains(Estado.BRISA)) {
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
        } else if (this.casillaActual.getEstados().contains(Estado.BRILLANTE)) {
            for (int i = 0; i < cAdyacentes.size(); i++) {
                if (cAdyacentes.get(i).getEstados().contains(Estado.POSIBLETESORO) && cAdyacentes.get(i).getEstados().contains(Estado.SEGURO)) {
                    CasillaAgente c = cAdyacentes.get(i);
                    Thread.sleep(1000);
                    this.prog.getVista().moverCharmander(this.getDireccion(c.getX(), c.getY()));
                    historial.push(this.casillaActual);
                    this.casillaActual = c;
                    percibirCasilla();
                    procesarEstados();
                    if (this.casillaActual.getEstados().contains(Estado.TESORO)) {
                        this.prog.getVista().quitarTesoro();
                        volver();
                    } else {
                        CasillaAgente cAnterior = this.historial.pop();
                        Thread.sleep(1000);
                        this.prog.getVista().moverCharmander(this.getDireccion(c.getX(), c.getY()));
                        this.casillaActual = cAnterior;
                    }
                }
            }
        }
        if (this.casillaActual.getEstados().contains(Estado.HEDOR)) {
            for (int i = 0; i < cAdyacentes.size(); i++) {
                if (!cAdyacentes.get(i).getEstados().contains(Estado.POSIBLEMONSTRUO)) {
//                        cAdyacentes.get(i).setEstados(Estado.MONSTRUO);
                    if (!cAdyacentes.get(i).getEstados().contains(Estado.POSIBLEPRECIPICIO)) {
                        if (!cAdyacentes.get(i).getVerificado()) {
                            this.prog.getVista().moverCharmander(this.getDireccion(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY()));
                            this.mover(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY());

                        } else {
                            CasillaAgente cAnterior = this.historial.pop();

                            this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
                            this.casillaActual = cAnterior;
                        }
                    } else {
                        CasillaAgente cAnterior = this.historial.pop();

                        this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
                        this.casillaActual = cAnterior;
                    }

                } else {
                    CasillaAgente cAnterior = this.historial.pop();

                    this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
                    this.casillaActual = cAnterior;
                }

            }
        } else {
            ArrayList<CasillaAgente> noVerificadas = (ArrayList<CasillaAgente>) cAdyacentes.clone();
            noVerificadas.removeIf(c -> c.getVerificado());

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
                                    break;
                                case SUR:
                                    this.casillaActual.setEstados(Estado.GOLPESUR);
                                    break;
                                case ESTE:
                                    this.casillaActual.setEstados(Estado.GOLPEESTE);
                                    break;
                                case OESTE:
                                    this.casillaActual.setEstados(Estado.GOLPEOESTE);
                                    break;
                            }
                            noVerificadas.remove(0);
                            try {
                                if (noVerificadas.size() > 0) {
                                    this.prog.getVista().moverCharmander(this.getDireccion(noVerificadas.get(0).getX(), noVerificadas.get(0).getY()));
                                    seguir = false;
//                                    mover(noVerificadas.get(0).getX(), noVerificadas.get(0).getY());
                                } else {
                                    CasillaAgente cAnterior = this.historial.pop();
                                    this.casillaActual = cAnterior;
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
                CasillaAgente cAnterior = this.historial.pop();
                try {
                    this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
                } catch (Exception ex) {

                }
                this.casillaActual = cAnterior;

            }
        }
    }

    private ArrayList<CasillaAgente> getAdyacentes() {
        ArrayList<CasillaAgente> cAdyacentes = new ArrayList<>();
        if (this.cuevaMemoria.get(casillaActual.getX()).containsKey(casillaActual.getY() + 1)) {
            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() + 1));
        } else {
//            if (this.casillaActual.getY() + 1 < this.prog.getCueva().getTamanyo()) {
            CasillaAgente ce = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() + 1);
            cAdyacentes.add(ce);
//            }
        }

        if (this.cuevaMemoria.containsKey(casillaActual.getX() + 1)) {
            if (this.cuevaMemoria.get(casillaActual.getX() + 1).containsKey(casillaActual.getY())) {
                cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() + 1).get(casillaActual.getY()));
            } else {
//                if (this.casillaActual.getX() + 1 < this.prog.getCueva().getTamanyo()) {
                CasillaAgente cs = new CasillaAgente(this.casillaActual.getX() + 1, this.casillaActual.getY());
                cAdyacentes.add(cs);
//                }
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
//            if (this.casillaActual.getY() - 1 >= 0) {
            CasillaAgente cw = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() - 1);
            cAdyacentes.add(cw);
//            }
        }

        if (this.cuevaMemoria.containsKey(casillaActual.getX() - 1)) {
            if (this.cuevaMemoria.get(casillaActual.getX() - 1).containsKey(casillaActual.getY())) {
                cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() - 1).get(casillaActual.getY()));
            } else {
//                if (this.casillaActual.getX() - 1 >= 0) {
                CasillaAgente cn = new CasillaAgente(this.casillaActual.getX() - 1, this.casillaActual.getY());
                cAdyacentes.add(cn);
//                }
            }
        } else {
//            if (this.casillaActual.getX() - 1 >= 0) {
            CasillaAgente cn = new CasillaAgente(this.casillaActual.getX() - 1, this.casillaActual.getY());
            cAdyacentes.add(cn);
//            }
        }

        return cAdyacentes;
    }

    public void setCharmander(Charmander m) {
        this.apariencia = m;
    }

    private boolean buscarTesoro(ArrayList<CasillaAgente> cAd) {
        for (int i = 0; i < cAd.size(); i++) {
            if (cAd.get(i).getX() >= 0 && cAd.get(i).getX() < this.prog.getCueva().getTamanyo()
                    && cAd.get(i).getY() >= 0 && cAd.get(i).getY() < this.prog.getCueva().getTamanyo()) {
                this.casillaActual = new CasillaAgente(cAd.get(i).getX(), cAd.get(i).getY());
                setCasillaActual(cAd.get(i));
                if (this.casillaActual.getEstados().contains(Estado.TESORO)) {
                    return true;
                }
                CasillaAgente cAnterior = this.historial.pop();
                this.casillaActual = new CasillaAgente(cAnterior.getX(), cAnterior.getY());
                setCasillaActual(cAnterior);
            }
        }
        return false;
    }

    public void volver() throws InterruptedException {
        while (!historial.isEmpty()) {
            Thread.sleep(1000);
            CasillaAgente cAnterior = this.historial.pop();
            this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
            setCasillaActual(cAnterior);
        }
        System.exit(0);
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

}
