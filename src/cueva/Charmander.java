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

    private Direccion direccion;

    public Charmander(int size) {
        this.size = size;
        this.setSize(this.size, this.size);

        direccion = Direccion.SUR;
        icono = new ImageIcon(charmanderSur.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));

    }

    public void moverCharmander(Direccion dir) {
        int y, x;
        int next;
        boolean llegado;

        y = this.getY();
        x = this.getX();

        llegado = false;

        switch (dir) {
            case NORTE:

                next = y - (this.getHeight() + 5);
                if (this.direccion != Direccion.NORTE) {
                    this.setIcon(icono = new ImageIcon(charmanderNorte.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }
                while (!llegado) {
                    if (this.getY() != next) {
                        this.setLocation(this.getX(), this.getY() - 1);
                        this.setIcon(icono = new ImageIcon(charmanderNortePaso.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                        this.repaint();

                        try {
                            //PARALIZACIÓN DE LA EJECUCIÓN DURANTE RETRASO/1000 SEGUNDOS
                            //PARA SIMULAR LA VELOCIDAD DEL MOVIMIENTO DE LA
                            //FIGURA ELIPSE
                            Thread.sleep(20);
                        } catch (InterruptedException err) {
                            System.out.println(err);
                        }
                    } else {
                        llegado = true;
                    }

                }
                this.setIcon(icono = new ImageIcon(charmanderNorte.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                break;
            case SUR:
                next = y + (this.getHeight() + 5);

                if (this.direccion != Direccion.SUR) {
                    this.setIcon(icono = new ImageIcon(charmanderSur.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }

                while (!llegado) {
                    if (this.getY() != next) {

                        this.setLocation(this.getX(), this.getY() + 1);
                        this.setIcon(icono = new ImageIcon(charmanderSurPaso.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                        this.repaint();

                        try {
                            //PARALIZACIÓN DE LA EJECUCIÓN DURANTE RETRASO/1000 SEGUNDOS
                            //PARA SIMULAR LA VELOCIDAD DEL MOVIMIENTO DE LA
                            //FIGURA ELIPSE
                            Thread.sleep(20);
                        } catch (InterruptedException err) {
                            System.out.println(err);
                        }
                    } else {
                        llegado = true;
                    }
                }
                this.setIcon(icono = new ImageIcon(charmanderSur.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                break;
            case ESTE:
                next = x + (this.getHeight() + 5);
                if (this.direccion != Direccion.ESTE) {
                    this.setIcon(icono = new ImageIcon(charmanderEste.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }

                while (!llegado) {
                    if (this.getY() != next) {

                        this.setLocation(this.getX() + 1, this.getY());
                        this.setIcon(icono = new ImageIcon(charmanderEstePaso.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                        this.repaint();

                        try {
                            //PARALIZACIÓN DE LA EJECUCIÓN DURANTE RETRASO/1000 SEGUNDOS
                            //PARA SIMULAR LA VELOCIDAD DEL MOVIMIENTO DE LA
                            //FIGURA ELIPSE
                            Thread.sleep(20);
                        } catch (InterruptedException err) {
                            System.out.println(err);
                        }
                    } else {
                        llegado = true;
                    }
                }
                this.setIcon(icono = new ImageIcon(charmanderEste.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                break;
            case OESTE:
                next = x - (this.getHeight() + 5);
                if (this.direccion != Direccion.OESTE) {
                    this.setIcon(icono = new ImageIcon(charmanderOeste.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }

                while (!llegado) {
                    if (this.getX() != next) {

                        this.setLocation(this.getX() - 1, this.getY());
                        this.setIcon(icono = new ImageIcon(charmanderOestePaso.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                        this.repaint();

                        try {
                            //PARALIZACIÓN DE LA EJECUCIÓN DURANTE RETRASO/1000 SEGUNDOS
                            //PARA SIMULAR LA VELOCIDAD DEL MOVIMIENTO DE LA
                            //FIGURA ELIPSE
                            Thread.sleep(20);
                        } catch (InterruptedException err) {
                            System.out.println(err);
                        }
                    } else {
                        llegado = true;
                    }
                }
                this.setIcon(icono = new ImageIcon(charmanderOeste.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                break;

        }

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

}
