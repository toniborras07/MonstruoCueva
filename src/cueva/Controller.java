package cueva;

import java.util.Random;
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
    private int id;
    Random r;
    private int init;
    View vista;

    public Controller(Main p, int id) {
        this.prog = p;
//        this.charmander = p.getAgente();
        this.vista = p.getVista();
        this.id = id;
        this.charmander = p.getAgente().get(id);
        r = new Random();
        init = id;

    }

    @Override
    public void run() {
        boolean dale = false;
        boolean inicio = true;
        while (!acabar) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (this.vista.isManual() && inicio) {
                charmander.percibirCasilla();
                charmander.addCasilla(charmander.getCasillaActual());
                charmander.procesarEstados();
                inicio = false;
                this.vista.setIniciar(false);

                dale = false;
            } else if (this.vista.isIniciar()) {
                charmander.percibirCasilla();
                charmander.addCasilla(charmander.getCasillaActual());
                charmander.procesarEstados();

                switch (init) {
                    case 0:
                         try {
                        Thread.sleep(r.nextInt(500) + 400);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    case 1:
                         try {
                        Thread.sleep(r.nextInt(1500) + 400);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    case 2:
                         try {
                        Thread.sleep(r.nextInt(2000) + 400);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    case 3:
                         try {
                        Thread.sleep(r.nextInt(2500) + 400);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }

                this.vista.setIniciar(false);
                dale = true;
            } else if (charmander.isEncontrado()) {
                charmander.volver();
                try {
                    Thread.sleep(this.vista.getDormir());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (charmander.isSalida()) {
                    acabar = true;
                }
            } else if (dale) {
                try {
//                    if (this.vista.isMonstruoMatado()) {
//                        this.prog.getCueva();
//                    }
                    charmander.razonar();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Thread.sleep(this.vista.getDormir());
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

    public void quitarTesoro(CasillaAgente c) {
        this.prog.getCueva().quitar(c.getX(), c.getY(), Estado.TESORO);
        this.prog.getVista().quitarTesoro(c.getX(), c.getY());
    }

    public void detener() {
        acabar = true;
    }

}
