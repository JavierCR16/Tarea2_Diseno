package Modelo;

public class Cliente {
    public int ID;
    public String nombre;

    public Cliente(String nombre, int id){
        this.nombre = nombre;
        this.ID = id;//TODO Sacar el ID de la base e insertar al cliente nuevo en la base
    }

    public void agregarTicket(String asunto){
        new Ticket(asunto, this);
    }
}
