package cueva;



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
    View2 vista;

    public Controller(Main p) {
        this.prog = p;
        this.charmander = p.getAscensor();
        this.vista = p.getVista();
    }

    @Override
    public void run() {
        int aviso = 0;
        int salidas = 0;
        while (!acabar) {
            

        }
    }

    private void espera(long m) {
        try {
            Thread.sleep(m);
        } catch (Exception e) {
        }
    }

  
    
}

