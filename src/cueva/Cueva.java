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
    
    public Cueva(Main p, int n) {
        this.prog = p;
        this.tamaño = n;
        cueva = new CasillaAgente[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                cueva[i][j] = new CasillaAgente(i,j);
                cueva[i][j].setEstados(Estado.VACIO);
            }
        }
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
