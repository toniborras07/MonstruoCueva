package cueva;

/**
 *
 * @author Laura del Mar y Antonio Borrás
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import javax.imageio.ImageIO;

class Casilla {

    //ATRIBUTOS
    private Rectangle2D.Float rec;

    private Boolean hoyo;
    private Boolean monster;
    private Boolean tesoro;
    private int numTesoros;
    private int numHoyos;
    private int numMonsters;

    public Casilla(Rectangle2D.Float r, Color c) {
        this.rec = r;
        this.hoyo = false;
        this.monster = false;
        this.tesoro = false;
        numHoyos = 0;
        numMonsters = 0;
        numTesoros = 0;
    }

    //Metodo que pinta una casilla segun sus valores booleanos
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        try {

            g.drawImage(ImageIO.read(new File("img/tierra.png")), (int) rec.x, (int) rec.y, null);

        } catch (Exception e) {

        }

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

}
