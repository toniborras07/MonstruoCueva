package cueva;

/**
 *
 * @author Laura del Mar y Antonio Borrás
 */
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Tablero1 extends JPanel {

    //ATRIBUTOS
    public static final int DIMENSION = 9;
    private static final int MAXIMO = 700;
    private static final int LADO = MAXIMO / DIMENSION;
    private static final Color NEGRO = Color.DARK_GRAY;
    private static final Color GRIS = Color.GRAY;
    private Casilla1 casilla[][];

    public Tablero1() {
        initComponents();

    }

    //Método encargado de estructurar la parte gráfca del tablero
    public void initComponents() {
        casilla = new Casilla1[DIMENSION][DIMENSION];
        int y = 0;
        for (int i = 0; i < DIMENSION; i++) {
            int x = 0;
            for (int j = 0; j < DIMENSION; j++) {
                Rectangle2D.Float r
                        = new Rectangle2D.Float(x, y, LADO, LADO);
                Color col;

                if ((i % 2 == 1 && j % 2 == 1) || (i % 2 == 0 && j % 2 == 0)) {
                    col = NEGRO;
                } else {
                    col = GRIS;
                }
                casilla[i][j] = new Casilla1(r, col);

                x += LADO;
            }
            y += LADO;
        }
    }

    //Metodo que pinta las casillas en el tablero
    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                casilla[i][j].paintComponent(g);
            }
        }
    }

//
//    //Metodo encargado de colocar un cero a la casilla en las posicion x,y
//    public void ponerCeros(int x, int y) {
//        casilla[x][y].setNumMines(0);
//    }
//
//    //Metodo que obtiene el numero de minas que una casilla tiene alrededor e
//    //incrementa en 1 su valor.
//    public void actualiza(int x, int y) {
//        casilla[x][y].setNumMines(casilla[x][y].getNunMines() + 1);
//    }
//
//    public void noBandera(int i, int j) {
//        casilla[i][j].setBanderita(false);
//    }
//    
//    public void noMina(int posx, int posy) {
//        casilla[posx][posy].setOcupada(false);
//    }
//
//    //BOOLEANOS
//    boolean dinsCasella(int i, int j, int x, int y) {
//        return casilla[i][j].getRec().contains(x, y);
//    }
//
//    public boolean isMina(int x, int y) {
//        return casilla[x][y].isOcupada();
//    }
//
//    public boolean isDestapada(int x, int y) {
//        return casilla[x][y].isPulsada();
//    }
//
//    public boolean isBandera(int i, int j) {
//        return casilla[i][j].isBanderita();
//    }
//
//    public boolean isSolucion(int i, int j) {
//        return casilla[i][j].isSolucion();
//    }
//
//
//    //GETTERS
//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(MAXIMO, MAXIMO);
//    }
//    
//    Rectangle getRectangle(int i, int j) {
//        return casilla[i][j].getRec().getBounds();
//    }
//
//    //SETTERS
//    public void setMina(int posx, int posy) {
//        casilla[posx][posy].setOcupada(true);
//    }
//
//    public void setTapada(int x, int y) {
//        casilla[x][y].setPulsada(false);
//    }
//
//    public void setExplotada(int x, int y) {
//        casilla[x][y].setExplotada(true);
//    }
//
//    public void setBandera(int i, int j) {
//        casilla[i][j].setBanderita(true);
//    }
//
//    public void setDestapada(int x, int y) {
//        casilla[x][y].setPulsada(true);
//    }
//
//    public void setSolucion(int i, int j, boolean b) {
//        casilla[i][j].setSolucion(b);
//    }
//
}
