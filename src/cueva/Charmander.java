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
    private Main m;

    private Direccion direccion;

    public Charmander(Main m) {
        this.size = m.getVista().getPrincipal().getWidth();
        this.setSize((m.getVista().getPrincipal().getWidth()) / m.getVista().getDimension(),
                (m.getVista().getPrincipal().getHeight()) / m.getVista().getDimension());

        direccion = Direccion.NORTE;
        icono = new ImageIcon(charmanderNorte.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
        this.m = m;
    }

    public void instanciar() {
        this.m.getVista().getMapa()[this.m.getVista().getDimension() - 1][0].add(this);
        this.setPosX(this.m.getVista().getDimension() - 1);
        this.setPosY(0);
        this.iniciarCharmander();
        m.getVista().repaint();
        

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

    public void moverCharmander(Direccion dir) {
        
        int next;
        boolean llegado;

        llegado = false;

        switch (dir) {
            case NORTE:

                if (this.getDireccion() != Direccion.NORTE) {
                    this.setDireccion(Direccion.NORTE);

                    this.setIcon(icono = new ImageIcon(charmanderNorte.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }
                this.m.getVista().getMapa()[this.getPosX() - 1][this.getPosY()].add(this);
                this.setPosX(this.getPosX() - 1);

                break;
            case SUR:

                if (this.getDireccion() != Direccion.SUR) {
                    this.setDireccion(Direccion.SUR);
                    this.setIcon(icono = new ImageIcon(charmanderSur.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }

                this.m.getVista().getMapa()[this.getPosX() + 1][this.getPosY()].add(this);
                this.setPosX(this.getPosX() + 1);
                break;
            case ESTE:

                if (this.getDireccion() != Direccion.ESTE) {
                    this.setDireccion(Direccion.ESTE);
                    this.setIcon(icono = new ImageIcon(charmanderEste.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }

                this.m.getVista().getMapa()[this.getPosX()][this.getPosY() + 1].add(this);
                this.setPosY(this.getPosY() + 1);

                break;
            case OESTE:

                if (this.getDireccion() != Direccion.OESTE) {
                    this.setDireccion(Direccion.OESTE);
                    this.setIcon(icono = new ImageIcon(charmanderOeste.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }

                this.m.getVista().getMapa()[this.getPosX()][this.getPosY() - 1].add(this);
                this.setPosY(this.getPosY() - 1);
           

                break;

        }
        this.m.getVista().repaint();

    }

}
