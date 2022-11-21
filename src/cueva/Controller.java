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
        int aviso = 0;
        int salidas = 0;
        while (!acabar) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (this.vista.isIniciar()) {
                System.out.println("fgdfuiyg");
                this.vista.moverCharmander(Direccion.SUR);
                try {
                    Thread.sleep(1000);
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

}
