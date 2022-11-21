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

    public Agente(Main p) {
        this.prog = p;

    }

    public Main getMain() {
        return this.prog;
    }

    public void setCasillaActual(CasillaAgente ca) {
        this.casillaActual = ca;
    }

    public CasillaAgente getCasillaActual() {
        return this.casillaActual;
    }

    public void addCasilla(CasillaAgente ca) {
        if (cuevaMemoria.get(ca.getX()) == null) {
            HashMap<Integer, CasillaAgente> hs = new HashMap();
            hs.put(ca.getY(), ca);
            cuevaMemoria.put(ca.getX(), hs);
        } else {
            cuevaMemoria.get(ca.getX()).put(ca.getY(), ca);
        }
    }

    public void mover(int x, int y) {
        historial.push(this.casillaActual);
        this.casillaActual = new CasillaAgente(x, y);
        percibirCasilla();
        procesarEstados();
        this.addCasilla(casillaActual);
    }

    public void percibirCasilla() {
        Cueva c = this.prog.getCueva();
        this.casillaActual.setEstados(c.getCasilla(this.casillaActual.getX(), this.casillaActual.getY()).getEstados());
        this.casillaActual.setVerificado(true);
    }

    public void procesarEstados() {
        ArrayList<CasillaAgente> cAdyacentes = this.getAdyacentes();

        if (this.casillaActual.getEstados().contains(Estado.VACIO)) {
            cAdyacentes.forEach((c) -> {
                if (!c.getVerificado()) {
                    c.setEstados(Estado.SEGURO);
                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });
        } else {

            if (this.casillaActual.getEstados().contains(Estado.GOLPE)) {
                CasillaAgente anterior = this.historial.pop();
                mover(anterior.getX(), anterior.getY());
            }

            if (this.casillaActual.getEstados().contains(Estado.BRISA)) {
                this.casillaActual.setEstados(Estado.SEGURO);
                cAdyacentes.forEach((c) -> {
                    if (!c.getVerificado()) {
                        c.setEstados(Estado.POSIBLEPRECIPICIO);
                        c.setVerificado(false);
                        this.addCasilla(c);
                    }
                });
            }

            if (this.casillaActual.getEstados().contains(Estado.HEDOR)) {
                this.casillaActual.setEstados(Estado.SEGURO);
                cAdyacentes.forEach((c) -> {
                    if (!c.getVerificado()) {
                        c.setEstados(Estado.POSIBLEMONSTRUO);
                        c.setVerificado(false);
                        this.addCasilla(c);
                    }
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
        } else {
            ArrayList<CasillaAgente> noVerificadas = cAdyacentes;
            noVerificadas.removeIf(c -> c.getVerificado());
            
            if(!noVerificadas.isEmpty()) {
                noVerificadas.forEach((c) -> {
                    if(c.getEstados().contains(Estado.SEGURO)) {
                        mover(c.getX(), c.getY());
                    }
                });
            } else {
                CasillaAgente cAnterior = this.historial.pop();
                this.casillaActual = new CasillaAgente(cAnterior.getX(), cAnterior.getY());
            }
        }
    }

    public ArrayList<CasillaAgente> getAdyacentes() {
        ArrayList<CasillaAgente> cAdyacentes = new ArrayList<>();
        if (this.casillaActual.getX() - 1 >= 0) {
            CasillaAgente cw = new CasillaAgente(this.casillaActual.getX() - 1, this.casillaActual.getY());
            cAdyacentes.add(cw);
        }
        if (this.casillaActual.getX() + 1 < this.prog.getCueva().getTamanyo()) {
            CasillaAgente ce = new CasillaAgente(this.casillaActual.getX() + 1, this.casillaActual.getY());
            cAdyacentes.add(ce);
        }
        if (this.casillaActual.getY() - 1 >= 0) {
            CasillaAgente cn = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() + 1);
            cAdyacentes.add(cn);
        }
        if (this.casillaActual.getY() + 1 < this.prog.getCueva().getTamanyo()) {
            CasillaAgente cs = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() - 1);
            cAdyacentes.add(cs);
        }

        return cAdyacentes;
    }

    public void setCharmander(Charmander m) {
        this.apariencia = m;
    }

    public boolean buscarTesoro(ArrayList<CasillaAgente> cAd) {
        for (int i = 0; i < cAd.size(); i++) {
            if (cAd.get(i).getX() >= 0 && cAd.get(i).getX() < this.prog.getCueva().getTamanyo()
                    && cAd.get(i).getY() >= 0 && cAd.get(i).getY() < this.prog.getCueva().getTamanyo()) {
                this.casillaActual = new CasillaAgente(cAd.get(i).getX(), cAd.get(i).getY());
                if (this.casillaActual.getEstados().contains(Estado.TESORO)) {
                    return true;
                }
                CasillaAgente cAnterior = this.historial.pop();
                this.casillaActual = new CasillaAgente(cAnterior.getX(), cAnterior.getY());
            }
        }
        return false;
    }

    public void volver() {
        while (historial.capacity() > 0) {
            CasillaAgente cAnterior = this.historial.pop();
            this.casillaActual = new CasillaAgente(cAnterior.getX(), cAnterior.getY());
        }
        //Acabar (quedarse quieto)
    }
}
