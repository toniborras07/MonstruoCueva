/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cueva;

import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class percepcionGrafica extends JLabel {

    private Estado estado;
    int ancho;
    int largo;

    public percepcionGrafica(int ancho, int largo) {
        this.largo=largo;
        this.ancho=ancho;
        this.setSize(largo,ancho);
    }

    public void meterEstado(Estado e, Icon icono1) {
        estado = e;
        this.setIcon(icono1);
        
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    

}
