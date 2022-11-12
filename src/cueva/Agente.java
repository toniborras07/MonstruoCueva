/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cueva;

import java.util.HashMap;

/**
 *
 * @author toniborras
 */
public class Agente {
    private HashMap<Integer, HashMap<Integer, CasillaAgente>> cuevaMemoria;
    private Main prog;
    private CasillaAgente casillaActual;

    public Agente(Main p){
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
        if(cuevaMemoria.get(ca.getX()) == null) {
            HashMap<Integer, CasillaAgente> hs = new HashMap();
            hs.put(ca.getY(),ca);
            cuevaMemoria.put(ca.getX(), hs);
        } else {
            cuevaMemoria.get(ca.getX()).put(ca.getY(), ca);
        }
    }
    
    public void moverNorte() {
        this.casillaActual = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() - 1);
        percibirCasilla();
        this.addCasilla(casillaActual);
    }
    
    public void moverSur() {
        this.casillaActual = new CasillaAgente(this.casillaActual.getX(), this.casillaActual.getY() + 1);
        percibirCasilla();
        this.addCasilla(casillaActual);
    }
    
    public void moverEste() {
        this.casillaActual = new CasillaAgente(this.casillaActual.getX() + 1, this.casillaActual.getY());
        percibirCasilla();
        this.addCasilla(casillaActual);
    }
    
    public void moverOeste() {
        this.casillaActual = new CasillaAgente(this.casillaActual.getX() - 1, this.casillaActual.getY());
        percibirCasilla();
        this.addCasilla(casillaActual);
    }
    
    public void percibirCasilla() {
        Cueva c = this.prog.getCueva();
        this.casillaActual.setEstados(c.getCasilla(this.casillaActual.getX(), this.casillaActual.getY()).getEstados());
    }
}
