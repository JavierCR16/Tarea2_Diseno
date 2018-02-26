package Modelo;

import Gestores.GestorBD;
import javafx.beans.property.SimpleStringProperty;

public class Cliente {
    public SimpleStringProperty ID;
    private SimpleStringProperty nombre;


    public GestorBD gestorBD;

    public Cliente(String nombre, String id){

        this.nombre = new SimpleStringProperty(nombre);
        this.ID = new SimpleStringProperty(id);//TODO Sacar el ID de la base e insertar al cliente nuevo en la base
        System.out.println(this.nombre);
    }

    public boolean agregarTicket(String asunto){
        return gestorBD.agregarTicket(asunto, Integer.valueOf(this.getNombre()));
    }

    public String getNombre() {
        return nombre.get();
    }


    public String getID() {
        return ID.get();
    }

    public SimpleStringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
}
