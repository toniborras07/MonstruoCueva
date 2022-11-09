package cueva;

/**
 *
 * @author  Antonio Borrás
 */
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Tablero extends JPanel {

    //ATRIBUTOS
    public static final int DIMENSION = 9;
    private static final int MAXIMO = 550;
    private static final int LADO = MAXIMO / DIMENSION;
    private static final Color NEGRO = Color.DARK_GRAY;
    private static final Color GRIS = Color.GRAY;
    private Casilla casilla[][];

    public Tablero() {
        initComponents();

    }

   
    //Método encargado de estructurar la parte gráfca del tablero
    
    private void initComponents() {
        casilla = new Casilla[DIMENSION][DIMENSION];
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
                casilla[i][j] = new Casilla(r, col);

                x += LADO;
            }
            y += LADO;
        }
    }

    //Metodo que pinta las casillas en el tablero
    @Override
    public void paintComponent(Graphics g) {
        int p=0;
        for (int i = 0; i < DIMENSION; i++) {
            p++;
            for (int j = 0; j < DIMENSION; j++) {
                casilla[i][j].paintComponent(g);
            }
        }
         p=0;
    }
    
       //GETTERS
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(550, 550);
    }
    

}
