package cueva;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Xavier Matas y Antonio Borrás
 */
public class Controller extends Thread {

    private static Main prog;
    private boolean acabar = false;
    Agente charmander;
    View vista;

    public Controller(Main p) {
        this.prog = p;
        this.charmander = p.getAgente();
        this.vista = p.getVista();
    }

    @Override
    public void run() {
        boolean dale = false;

        while (!acabar) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (this.vista.isIniciar() || this.vista.isManual()) {
                charmander.percibirCasilla();
                charmander.addCasilla(charmander.getCasillaActual());
                charmander.procesarEstados();

                this.vista.setIniciar(false);
                dale = true;
            } else if(charmander.isEncontrado()) {
                charmander.volver();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(charmander.isSalida()) {
                    acabar = true;
                }
            } else if (dale) {
                try {
                    charmander.razonar();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    private void espera(long m) {
        try {
            Thread.sleep(m);
        } catch (Exception e) {
        }
    }
    
    public void quitarTesoro(CasillaAgente c){
        this.prog.getCueva().quitar(c.getX(), c.getY(), Estado.TESORO);
        this.prog.getVista().quitarTesoro(c.getX(), c.getY());
    }

}
