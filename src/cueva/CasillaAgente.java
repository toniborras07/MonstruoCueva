/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cueva;

import java.util.ArrayList;

/**
 *
 * @author XAMAP
 */
public class CasillaAgente {
    private int x,y;
    private ArrayList<Estado> estado;
    private boolean verificado;
    
    public CasillaAgente(int x, int y) {
        this.x = x;
        this.y = y;
        this.estado = new ArrayList<>();
    }
    
    public void setVerificado(boolean b) {
        this.verificado = b;
    }
    
    public boolean getVerificado() {
        return this.verificado;
    }
    
    public void setEstados(Estado e) {
        if(this.estado.size() == 1 && this.estado.get(0) == Estado.VACIO) {
            this.estado.remove(0);
        }
        this.estado.add(e);
    }
    
    public void setEstados(ArrayList<Estado> e) {
        this.estado = e;
    }
    
    public ArrayList<Estado> getEstados() {
        return this.estado;
    }
    
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    
}

