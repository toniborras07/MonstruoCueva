/*
CLASE Tablero de casillas

realización: Juan Montes de Oca
 */
package cueva;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Tablero extends JPanel {

    //DECLARACIÓN DE ATRIBUTOS
    private int columnas;
    private int filas;
    private Casilla[][] casillas;
//    private FileDatosCasillaIn f;
    private int[] salida;
//    private Ficha bola;
    //MÉTODO CONSTRUCTOR
    public Tablero(String p) {
        Inicializar(p);

    }

    public Tablero() {
        

    }

    //MÉTODO QUE DEVUELVE EL TAMAÑO QUE DEBERÍA TENER EL TABLERO PARA UNA VISUALIZACIÓN
    //TOTAL DE SUS COMPONENTES
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 752);
    }

    //MÉTODO Paint
    @Override
    public void paintComponent(Graphics g) {

        //declaración objeto Graphics2D
        Graphics2D g2 = (Graphics2D) g;
        //dibujo de todas las casillas del tablero

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {

                g2.setColor(Color.yellow);
                g2.fillRect((int) casillas[fila][columna].getX(),
                        (int) casillas[fila][columna].getY(), 60, 60);

                
                if (casillas[fila][columna].isOcupada()) {
//                        bola=new Ficha(30,(int) casillas[fila][columna].getX() + 10,
//                            (int) casillas[fila][columna].getY() + 10,Color.black, Color.BLUE);
//                        bola.dibujar(g2,"meliodas");
//                   
                }

                if (casillas[fila][columna].isSalida()) {
                    try {
                        BufferedImage imagenFichero = ImageIO.read(new File("salida.png"));
                        g2.drawImage(imagenFichero.getScaledInstance(50, 50, 0), (int) casillas[fila][columna].getX(),
                                (int) casillas[fila][columna].getY(), this);
                    } catch (IOException ex) {
                        Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        }
//            BufferedImage imagenFichero = ImageIO.read(new File("salida.png"));
//            g2.drawImage(imagenFichero.getScaledInstance(50, 50, 0), 100,300, this);
//        } catch (IOException ex) {
//            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void Inicializar(String p) {
//        f = new FileDatosCasillaIn(p);
        this.filas = 10;
        this.columnas = 8;

        casillas = new Casilla[filas][columnas];

        for (int fila = 0; fila < filas; fila++) {
            int x = 0;
            int y = fila * 60;
            for (int columna = 0; columna < columnas; columna++) {
//                casillas[fila][columna] = f.lecturaCasilla();
                casillas[fila][columna].setX(x);
                casillas[fila][columna].setY(y);
//                System.out.println(casillas[fila][columna].toString());
                x = x + 60;
            }
        }
       
//        salida = f.getSalida();
//        casillas[salida[0]][salida[1]].setSalida(true);
    }

    //Método de acceso a la coordenada X de la casilla correspondiente a la fila y
    //columna dadas por parámetro
    public int getX(int fila, int columna) {
        return (int) casillas[fila][columna].getX();
    }

    //Método de acceso a la coordenada Y de la casilla correspondiente a la fila y
    //columna dadas por parámetro    
    public int getY(int fila, int columna) {
        return (int) casillas[fila][columna].getY();
    }

    //Método de acceso al número de filas o columnas del tablero   
    public int getNumCasillas() {
        return columnas;
    }

    //Método que cambia el estado de la casilla correspondiente a la fila y
    //columna dadas por parámetro    
    public void cambiarEstadoCasilla(int fila, int columna) {
        casillas[fila][columna].cambiarEstado();
    }

    //Método que devuelve el estado de la casilla correspondiente a la fila y
    //columna dadas por parámetro    
    public boolean getEstadoCasilla(int fila, int columna) {
        return casillas[fila][columna].estado();
    }

    //Método que libera a todas las casillas del tablero.
    public void borrar() {
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                casillas[fila][columna].setLiberada();
            }
        }
    }



    public Casilla getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }

    public int getNumFilas() {
        return filas;
    }

    public int getNumColumnas() {
        return columnas;
    }
    
    

}
