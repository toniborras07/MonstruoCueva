/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cueva;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author toniborras
 */
public class Main {

    static Semaphore semaforoCueva = new Semaphore(1);
    static Semaphore semaforoTesoros = new Semaphore(1);
    private int numTesoros;
    private ArrayList<Agente> agentes;    // Punter al Model del patró
    private Cueva cueva;
    private View vis;    // Punter a la Vista del patró
//    private Controller con;  // punter al Control del patró
    private ArrayList<Controller> controladores;
    private int numAgentes;

    private void inicio() {
        numAgentes = 1;
        numTesoros = 0;
        controladores = new ArrayList();
        agentes = new ArrayList();
        cueva = new Cueva(this, Tamanyo.PEQUEÑO);
        vis = new View(this, Tamanyo.PEQUEÑO);
        vis.mostrar();
        agentes.add(new Agente(this, numAgentes - 1, 0));
        controladores.add(new Controller(this, numAgentes - 1));
//        vis.setNumAgentes(numAgentes++);
        controladores.get(0).start();

    }

    public static void main(String[] args) {
        (new Main()).inicio();
    }

    //metodo para conseguir el modelo de datos
    public ArrayList<Agente> getAgente() {
        return this.agentes;
    }

    //metodo para devolver la vista 
    public View getVista() {
        return this.vis;
    }

    //metodo que devuelve el controlador
    public ArrayList<Controller> getController() {
        return controladores;
    }

    public Cueva getCueva() {
        return this.cueva;
    }

    public int getNumAgentes() {
        return numAgentes;
    }

    public void setNumAgentes(int numAgentes) {
        this.numAgentes = numAgentes;
    }

    public int getNumTesoros() {
        return numTesoros;
    }

    public void setNumTesoros(int numTesoros) {
        this.numTesoros = numTesoros;
    }

    public void addTesoro() {
        this.numTesoros++;
    }
    
    public void quitarTesoro() {
        this.numTesoros--;
    }
}
