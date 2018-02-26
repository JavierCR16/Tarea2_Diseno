package Modelo;

import Gestores.GestorBD;

public class Cliente {
    public int ID;
    private String nombre;
    public GestorBD gestorBD;

    public Cliente(String nombre, int id){

        this.nombre = nombre;
        this.ID = id;//TODO Sacar el ID de la base e insertar al cliente nuevo en la base
    }

    public boolean agregarTicket(String asunto){
        return gestorBD.agregarTicket(asunto, this.ID);
    }
}
