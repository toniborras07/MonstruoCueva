package cueva;

/**
 *
 * @author Laura del Mar y Antonio Borrás
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class CasillaGrafica extends JLabel {

    //ATRIBUTOS
    private ImageIcon tierra = new ImageIcon("src/img/piedra.png");
    private Icon icono;
    private Boolean hoyo;
    private Boolean monster;
    private Boolean tesoro;
    private Boolean entrada;
    private int numTesoros;
    private int numHoyos;
    private int numMonsters;
    private int size;
    private ArrayList<percepcionGrafica> percepciones;
//    private ImageIcon hedor = new ImageIcon("src/img/hedor.png");
//    private Icon iconohedor = new ImageIcon(hedor.getImage().getScaledInstance(this.getWidth() / 3, this.getHeight() / 3, Image.SCALE_DEFAULT));
//    ImageIcon brisa = new ImageIcon("src/img/brisa.png");
//    Icon iconobrisa = new ImageIcon(brisa.getImage().getScaledInstance(this.getWidth() / 3, this.getHeight() / 3, Image.SCALE_DEFAULT));

    public CasillaGrafica(int size) {
        this.size = size;
        this.setSize(size, size);
        percepciones= new ArrayList();
        icono = new ImageIcon(tierra.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
//        this.setLayout(new GridLayout(2,1));
        this.hoyo = false;
        this.monster = false;
        this.tesoro = false;
        this.entrada = false;
        numHoyos = 0;
        numMonsters = 0;
        numTesoros = 0;
    }

    public void ponerImagen() {
        this.setIcon(icono);
    }

    public Boolean getHoyo() {
        return hoyo;
    }

    public void setHoyo(Boolean hoyo) {
        this.hoyo = hoyo;
    }

    public Boolean getMonster() {
        return monster;
    }

    public void setMonster(Boolean monster) {
        this.monster = monster;
    }

    public Boolean getTesoro() {
        return tesoro;
    }

    public void setTesoro(Boolean tesoro) {
        this.tesoro = tesoro;
    }

    public int getNumTesoros() {
        return numTesoros;
    }

    public void setNumTesoros(int numTesoros) {
        this.numTesoros = numTesoros;
    }

    public int getNumHoyos() {
        return numHoyos;
    }

    public void setNumHoyos(int numHoyos) {
        this.numHoyos = numHoyos;
    }

    public int getNumMonsters() {
        return numMonsters;
    }

    public void setNumMonsters(int numMonsters) {
        this.numMonsters = numMonsters;
    }

    public Boolean getEntrada() {
        return entrada;
    }

    public void setEntrada(Boolean entrada) {
        this.entrada = entrada;
    }

    public void meterPercepcion(percepcionGrafica g) {
        percepciones.add(g);
        this.add(g);
    }

    public ArrayList<percepcionGrafica> getPercepciones() {
        return percepciones;
    }

    public void setPercepciones(ArrayList<percepcionGrafica> percepciones) {
        this.percepciones = percepciones;
    }
    
    
    

}
