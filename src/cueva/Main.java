/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cueva;

/**
 *
 * @author toniborras
 */
public class Main {
    private Agente charmander;    // Punter al Model del patró
    private Cueva cueva;
    private View vis;    // Punter a la Vista del patró
    private Controller con;  // punter al Control del patró
    
    private void inicio() {
        charmander = new Agente(this);
        cueva = new Cueva(this, Tamanyo.PEQUEÑO);
        vis = new View(this,Tamanyo.PEQUEÑO);
        vis.mostrar();
        con = new Controller(this);
        charmander.setCharmander(vis.getCharmander());
        con.start();       
        
    }
    
    
    
    public static void main(String [] args) {
        (new Main()).inicio();
    }
    
    //metodo para conseguir el modelo de datos
    public Agente getAgente() {
        return this.charmander;
    }

    //metodo para devolver la vista 
    public View getVista() {
        return this.vis;
    }

    //metodo que devuelve el controlador
    public Controller getController() {
        return con;
    }
    
    public Cueva getCueva() {
        return this.cueva;
    }
}
