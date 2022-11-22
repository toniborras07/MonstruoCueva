/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cueva;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author toniborras
 */
public class Charmander extends JLabel {

    private int size;

    private ImageIcon charmanderEste = new ImageIcon("src/img/charmanderEste.png");
    private ImageIcon charmanderEstePaso = new ImageIcon("src/img/charmanderEstePaso.png");
    private ImageIcon charmanderNorte = new ImageIcon("src/img/charmanderNorte.png");
    private ImageIcon charmanderNortePaso = new ImageIcon("src/img/charmanderNortePaso.png");
    private ImageIcon charmanderOeste = new ImageIcon("src/img/charmanderOeste.png");
    private ImageIcon charmanderOestePaso = new ImageIcon("src/img/charmanderOestePaso.png");
    private ImageIcon charmanderSur = new ImageIcon("src/img/charmanderSur.png");
    private ImageIcon charmanderSurPaso = new ImageIcon("src/img/charmanderSurPaso.png");
    Icon icono;
    private int posX;
    private int posY;


    private Direccion direccion;

    public Charmander(int size) {
        this.size = size;
        this.setSize(this.size, this.size);
       
        direccion = Direccion.SUR;
        icono = new ImageIcon(charmanderSur.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));

    }

    

    public void iniciarCharmander() {
        this.setIcon(icono);
    }

    public int tamanyo(Tamanyo t) {
        switch (t) {
            case PEQUEÑO:
                return 8;

            case MEDIANO:
                return 12;

            case GRANDE:
                return 16;
        }
        return 0;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    
    public int getPosX() {
        return posX;
    }

    public void setPosX(int x) {
        this.posX = x;
    }

    
    public int getPosY() {
        return posY;
    }

    public void setPosY(int y) {
        this.posY = y;
    }
    
    
    


    
    

}
