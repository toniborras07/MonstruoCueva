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
public class Pokemon extends JLabel {

    private int size;

    private ImageIcon pokemonEste;
    private ImageIcon pokemonNorte;
    private ImageIcon pokemonOeste;
    private ImageIcon pokemonSur;
    Icon icono;
    private int posX;
    private int posY;
    private Main m;

    private Direccion direccion;

    public Pokemon(Main m, int id) {
        this.size = m.getVista().getPrincipal().getWidth();
        this.setSize((m.getVista().getPrincipal().getWidth()) / m.getVista().getDimension(),
                (m.getVista().getPrincipal().getHeight()) / m.getVista().getDimension());

        switch (id) {
            case 0:
                direccion = Direccion.NORTE;
                pokemonEste = new ImageIcon("src/img/charmanderEste.png");
                pokemonNorte = new ImageIcon("src/img/charmanderNorte.png");
                pokemonOeste = new ImageIcon("src/img/charmanderOeste.png");
                pokemonSur = new ImageIcon("src/img/charmanderSur.png");
                break;
            case 1:
                direccion = Direccion.SUR;
                pokemonEste = new ImageIcon("src/img/bulbasaurEste.png");
                pokemonNorte = new ImageIcon("src/img/bulbasaurNorte.png");
                pokemonOeste = new ImageIcon("src/img/bulbasaurOeste.png");
                pokemonSur = new ImageIcon("src/img/bulbasaurSur.png");
                break;
            case 2:
                direccion = Direccion.NORTE;
                pokemonEste = new ImageIcon("src/img/squirtleEste.png");
                pokemonNorte = new ImageIcon("src/img/squirtleNorte.png");
                pokemonOeste = new ImageIcon("src/img/squirtleOeste.png");
                pokemonSur = new ImageIcon("src/img/squirtleSur.png");
                break;
            case 3:
                direccion = Direccion.SUR;
                pokemonEste = new ImageIcon("src/img/pikachuEste.png");
                pokemonNorte = new ImageIcon("src/img/pikachuNorte.png");
                pokemonOeste = new ImageIcon("src/img/pikachuOeste.png");
                pokemonSur = new ImageIcon("src/img/pikachuSur.png");
                break;
        }

        this.m = m;
    }

    public void instanciar(int id) {
        switch (id) {
            case 0:
                this.m.getVista().getMapa()[this.m.getVista().getDimension() - 1][0].add(this);
                this.setPosX(this.m.getVista().getDimension() - 1);
                this.setPosY(0);
                this.iniciarPokemon(id);
                m.getVista().repaint();
                break;
            case 1:
                this.m.getVista().getMapa()[0][this.m.getVista().getDimension() - 1].add(this);
                this.setPosX(0);
                this.setPosY(this.m.getVista().getDimension() - 1);
                this.iniciarPokemon(id);
                m.getVista().repaint();
                break;
            case 2:
                this.m.getVista().getMapa()[this.m.getVista().getDimension() - 1][this.m.getVista().getDimension() - 1].add(this);
                this.setPosX(this.m.getVista().getDimension() - 1);
                this.setPosY(this.m.getVista().getDimension() - 1);
                this.iniciarPokemon(id);
                m.getVista().repaint();
                break;
            case 3:
                this.m.getVista().getMapa()[0][0].add(this);
                this.setPosX(0);
                this.setPosY(0);
                this.iniciarPokemon(id);
                m.getVista().repaint();
                break;
        }

    }

    public void iniciarPokemon(int id) {
        switch (id) {
            case 0:
                icono = new ImageIcon(pokemonNorte.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
                break;
            case 1:
                icono = new ImageIcon(pokemonSur.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
                break;
            case 2:
                icono = new ImageIcon(pokemonNorte.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
                break;
            case 3:
                icono = new ImageIcon(pokemonSur.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
                break;

        }
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

    public void moverPokemon(Direccion dir) {

        int next;
        boolean llegado;

        llegado = false;

        switch (dir) {
            case NORTE:

                if (this.getDireccion() != Direccion.NORTE) {
                    this.setDireccion(Direccion.NORTE);

                    this.setIcon(icono = new ImageIcon(pokemonNorte.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }
                this.m.getVista().getMapa()[this.getPosX() - 1][this.getPosY()].add(this);
                this.setPosX(this.getPosX() - 1);

                break;
            case SUR:

                if (this.getDireccion() != Direccion.SUR) {
                    this.setDireccion(Direccion.SUR);
                    this.setIcon(icono = new ImageIcon(pokemonSur.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }

                this.m.getVista().getMapa()[this.getPosX() + 1][this.getPosY()].add(this);
                this.setPosX(this.getPosX() + 1);
                break;
            case ESTE:

                if (this.getDireccion() != Direccion.ESTE) {
                    this.setDireccion(Direccion.ESTE);
                    this.setIcon(icono = new ImageIcon(pokemonEste.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }

                this.m.getVista().getMapa()[this.getPosX()][this.getPosY() + 1].add(this);
                this.setPosY(this.getPosY() + 1);

                break;
            case OESTE:

                if (this.getDireccion() != Direccion.OESTE) {
                    this.setDireccion(Direccion.OESTE);
                    this.setIcon(icono = new ImageIcon(pokemonOeste.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT)));

                }

                this.m.getVista().getMapa()[this.getPosX()][this.getPosY() - 1].add(this);
                this.setPosY(this.getPosY() - 1);

                break;

        }
        this.m.getVista().repaint();

    }

}
