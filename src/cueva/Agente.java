/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cueva;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author toniborras
 */
public class Agente {

    private HashMap<Integer, HashMap<Integer, CasillaAgente>> cuevaMemoria;
    private Main prog;
    private CasillaAgente casillaActual;

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
        //TODO Comprobar si la casilla está en su base de conocimientos o no
        this.casillaActual = new CasillaAgente(x, y);
        percibirCasilla();
        procesarEstados();
        this.addCasilla(casillaActual);
        razonar();
    }

    public void percibirCasilla() {
        Cueva c = this.prog.getCueva();
        this.casillaActual.setEstados(c.getCasilla(this.casillaActual.getX(), this.casillaActual.getY()).getEstados());
        this.casillaActual.setVerificado(true);
    }

    public void procesarEstados() {
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
        
        if(this.casillaActual.getEstados().contains(Estado.VACIO)) {
            cAdyacentes.forEach((c) -> {
                if(!c.getVerificado()){
                    c.setEstados(Estado.SEGURO);
                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });
        }
        
        if (this.casillaActual.getEstados().contains(Estado.GOLPE)) {
           //TODO Volver atrás
        }
        
        if (this.casillaActual.getEstados().contains(Estado.BRISA)) {
            cAdyacentes.forEach((c) -> {
                if(!c.getVerificado()){
                    c.setEstados(Estado.PRECIPICIO); 
                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });
        }
        
        if (this.casillaActual.getEstados().contains(Estado.HEDOR)) {
            cAdyacentes.forEach((c) -> {
                if(!c.getVerificado()){
                    c.setEstados(Estado.MONSTRUO); 
                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });
        }
        
        if (this.casillaActual.getEstados().contains(Estado.BRILLANTE)) {
            cAdyacentes.forEach((c) -> {
                if(!c.getVerificado()){
                    c.setEstados(Estado.TESORO); 
                    c.setVerificado(false);
                    this.addCasilla(c);
                }
            });
        }
    }
    
    public void razonar() {
        
    }
}
