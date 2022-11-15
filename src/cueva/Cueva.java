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
public class Cueva {
    private CasillaAgente[][] cueva;
    private int tamaño;
    private Main prog;
    
    public Cueva(Main p, Tamanyo n) {
        this.prog = p;
        this.tamaño = tamanyo(n);
        cueva = new CasillaAgente[tamaño][tamaño];
        for(int i = 0; i < tamaño; i++) {
            for(int j = 0; j < tamaño; j++) {
                cueva[i][j] = new CasillaAgente(i,j);
                cueva[i][j].setEstados(Estado.VACIO);
            }
        }
    }
    
    public int getTamanyo() {
        return this.tamaño;
    }
    
    public int tamanyo(Tamanyo t){
        switch(t){
            case PEQUEÑO:
                return 8;
                
            case MEDIANO:
                return 12;
                
            case GRANDE:
                return 16;
        }
        return 0;
    }
    
    public void setTesoro(int i, int j) {
        this.cueva[i][j].setEstados(Estado.TESORO);
        if(i > 0) {
            this.cueva[i-1][j].setEstados(Estado.BRILLANTE);
        }
        
        if(i < this.tamaño - 1) {
            this.cueva[i+1][j].setEstados(Estado.BRILLANTE);
        }
        
        if(j > 0) {
            this.cueva[i][j-1].setEstados(Estado.BRILLANTE);
        }
        
        if(j < this.tamaño - 1) {
            this.cueva[i][j+1].setEstados(Estado.BRILLANTE);
        }
    }
    
    public void setMonstruo(int i, int j) {
        this.cueva[i][j].setEstados(Estado.MONSTRUO);
        if(i > 0) {
            this.cueva[i-1][j].setEstados(Estado.HEDOR);
        }
        
        if(i < this.tamaño - 1) {
            this.cueva[i+1][j].setEstados(Estado.HEDOR);
        }
        
        if(j > 0) {
            this.cueva[i][j-1].setEstados(Estado.HEDOR);
        }
        
        if(j < this.tamaño - 1) {
            this.cueva[i][j+1].setEstados(Estado.HEDOR);
        }
    }
    
    public void setPrecipicio(int i, int j) {
        this.cueva[i][j].setEstados(Estado.PRECIPICIO);
        if(i > 0) {
            this.cueva[i-1][j].setEstados(Estado.BRISA);
        }
        
        if(i < this.tamaño - 1) {
            this.cueva[i+1][j].setEstados(Estado.BRISA);
        }
        
        if(j > 0) {
            this.cueva[i][j-1].setEstados(Estado.BRISA);
        }
        
        if(j < this.tamaño - 1) {
            this.cueva[i][j+1].setEstados(Estado.BRISA);
        }
    }
    
    public CasillaAgente getCasilla(int x, int y) {
        return this.cueva[x][y];
    }
}
