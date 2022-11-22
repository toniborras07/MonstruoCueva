/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cueva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

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
        this.casillaActual.setEstados(c.getCasilla(this.casillaActual.getX(), this.casillaActual.getY()).getEstados());
        this.casillaActual.setVerificado(true);
        this.casillaActual.setEstados(Estado.SEGURO);
    }

    public void procesarEstados() {
        ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes();

        if (this.casillaActual.getEstados().contains(Estado.SEGURO)
                || this.casillaActual.getEstados().contains(Estado.VACIO)) {
            cAdyacentes.forEach((c) -> {
                if (!c.getVerificado()) {
                    c.setEstados(Estado.SEGURO);
//                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });
        } else {

            if (this.casillaActual.getEstados().contains(Estado.BRISA) && !this.casillaActual.getEstados().contains(Estado.HEDOR)) {
//                this.casillaActual.setEstados(Estado.SEGURO);
                cAdyacentes.forEach((c) -> {
                    if (!c.getVerificado() && !c.getEstados().contains(Estado.SEGURO)) {
                        if (c.getEstados().contains(Estado.POSIBLEMONSTRUO)) {
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
//                this.casillaActual.setEstados(Estado.SEGURO);
                cAdyacentes.forEach((c) -> {
                    if (!c.getVerificado()) {
                        if (c.getEstados().contains(Estado.POSIBLEPRECIPICIO)) {
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
                    if (!c.getVerificado()) {
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
                        c.setEstados(Estado.POSIBLETESORO);
                        c.setVerificado(false);
                        this.addCasilla(c);
                    }
                });
            }
        }
    }

    public void razonar() {
        ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes();

        if (this.casillaActual.getEstados().contains(Estado.BRILLANTE) && this.casillaActual.getEstados().size() == 1) {
            if (buscarTesoro(cAdyacentes)) {
                volver();
            }
        } else if (this.casillaActual.getEstados().contains(Estado.HEDOR) || this.casillaActual.getEstados().contains(Estado.BRISA)) {
            ArrayList<CasillaAgente> noVerificadasSeguras = cAdyacentes;
            noVerificadasSeguras.removeIf(c -> c.getVerificado() || !c.getEstados().contains(Estado.SEGURO));

            if (!noVerificadasSeguras.isEmpty()) {
//                noVerificadasSeguras.forEach((c) -> {
                mover(noVerificadasSeguras.get(0).getX(), noVerificadasSeguras.get(0).getY());
                this.prog.getVista().moverCharmander(this.getDireccion(noVerificadasSeguras.get(0).getX(), noVerificadasSeguras.get(0).getY()));
//                });
            } else {
//                cAdyacentes.forEach((c) -> {
                int menosVisto = 0;
                int comp = 10000;
                for (int i = 0; i < cAdyacentes.size(); i++) {
                    if (cAdyacentes.get(i).getEstados().contains(Estado.SEGURO)) {
                        menosVisto = i;

                        if (comp > cAdyacentes.get(i).getNumVisitas()) {
                            comp = cAdyacentes.get(i).getNumVisitas();
                            menosVisto = i;
                        }
                    }
                }
                mover(cAdyacentes.get(menosVisto).getX(), cAdyacentes.get(menosVisto).getY());
                this.prog.getVista().moverCharmander(this.getDireccion(cAdyacentes.get(menosVisto).getX(), cAdyacentes.get(menosVisto).getY()));

//                });
            }
        } else if (this.casillaActual.getEstados().contains(Estado.BRILLANTE)) {
            for (int i = 0; i < cAdyacentes.size(); i++) {
                if (cAdyacentes.get(i).getEstados().contains(Estado.POSIBLETESORO)) {
//                        cAdyacentes.get(i).setEstados(Estado.TESORO);
//                        //IDEA: ORDENAR QUE EL AGENTE SE MUEVA A ESA CASILLA YA QUE SE ASEGURA QUE ESTA EL TESORO
//                        this.encontrado = true;
                }
            }
        }
        if (this.casillaActual.getEstados().contains(Estado.HEDOR)) {
            for (int i = 0; i < cAdyacentes.size(); i++) {
                if (!cAdyacentes.get(i).getEstados().contains(Estado.POSIBLEMONSTRUO)) {
//                        cAdyacentes.get(i).setEstados(Estado.MONSTRUO);
                    if (!cAdyacentes.get(i).getEstados().contains(Estado.POSIBLEPRECIPICIO)) {
                        if (!cAdyacentes.get(i).getVerificado()) {
                            this.mover(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY());
                            this.prog.getVista().moverCharmander(this.getDireccion(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY()));

                        } else {
                            this.volver();
                            this.mover();
                            this.prog.getVista().moverCharmander(this.getDireccion(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY()));
                        }
                    } else {
                        this.volver();
                        this.mover();
                        this.prog.getVista().moverCharmander(this.getDireccion(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY()));
                    }

                } else {
                    this.volver();
                    this.mover();
                    this.prog.getVista().moverCharmander(this.getDireccion(cAdyacentes.get(i).getX(), cAdyacentes.get(i).getY()));
                }

            }
        } else {
            ArrayList<CasillaAgente> noVerificadas = cAdyacentes;
            noVerificadas.removeIf(c -> c.getVerificado());

            if (!noVerificadas.isEmpty()) {
//                noVerificadas.forEach((c) -> {
                if (noVerificadas.get(0).getEstados().contains(Estado.SEGURO)) {
                    mover(noVerificadas.get(0).getX(), noVerificadas.get(0).getY());
                    this.prog.getVista().moverCharmander(this.getDireccion(noVerificadas.get(0).getX(), noVerificadas.get(0).getY()));
                }
//                });
            } else {
                CasillaAgente cAnterior = this.historial.pop();

                this.prog.getVista().moverCharmander(this.getDireccion(cAnterior.getX(), cAnterior.getY()));
                this.casillaActual = new CasillaAgente(cAnterior.getX(), cAnterior.getY());

            }
        }
    }

    private ArrayList<CasillaAgente> getAdyacentes() {
        ArrayList<CasillaAgente> cAdyacentes = new ArrayList<>();
        if (this.cuevaMemoria.containsKey(casillaActual.getX() + 1)) {
            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() + 1).get(casillaActual.getY()));
        } else {
            if (this.casillaActual.getX() + 1 < this.prog.getCueva().getTamanyo()) {
                CasillaAgente ce = new CasillaAgente(this.casillaActual.getX() + 1, this.casillaActual.getY());
                cAdyacentes.add(ce);
            }
        }

        if (this.cuevaMemoria.get(casillaActual.getX()).containsKey(casillaActual.getY() + 1)) {
            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() + 1));
        } else {
            if (this.casillaActual.getY() + 1 < this.prog.getCueva().getTamanyo()) {
                CasillaAgente cs = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() + 1);
                cAdyacentes.add(cs);
            }
        }

        if (this.cuevaMemoria.containsKey(casillaActual.getX() - 1)) {
            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX() - 1).get(casillaActual.getY()));
        } else {
            if (this.casillaActual.getX() - 1 >= 0) {
                CasillaAgente cw = new CasillaAgente(this.casillaActual.getX() - 1, this.casillaActual.getY());
                cAdyacentes.add(cw);
            }
        }

        if (this.cuevaMemoria.get(casillaActual.getX()).containsKey(casillaActual.getY() - 1)) {
            cAdyacentes.add(this.cuevaMemoria.get(casillaActual.getX()).get(casillaActual.getY() - 1));
        } else {
            if (this.casillaActual.getY() - 1 > 0) {
                CasillaAgente cn = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() - 1);
                cAdyacentes.add(cn);
            }
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

    public void volver() {
        while (historial.capacity() > 0) {
            CasillaAgente cAnterior = this.historial.pop();
            setCasillaActual(cAnterior);
        }
        //Acabar (quedarse quieto)
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
            return Direccion.OESTE;
        }
        if (this.casillaActual.getX() > x) {
            return Direccion.ESTE;
        }
        if (this.casillaActual.getY() < y) {
            return Direccion.NORTE;
        }
        return Direccion.SUR;
    }

}
