package Modelo;

import Gestores.GestorBD;
import javafx.beans.property.SimpleStringProperty;

public class Cliente {
    public SimpleStringProperty ID;
    private SimpleStringProperty nombre;
    public GestorBD gestorBD;

    public Cliente(String nombre, String id){
        this.nombre = new SimpleStringProperty(nombre);
        this.ID = new SimpleStringProperty(id);
    }

    public boolean agregarTicket(String asunto){
        return gestorBD.agregarTicket(asunto, Integer.valueOf(this.getID()));
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getID() {
        return ID.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
}
