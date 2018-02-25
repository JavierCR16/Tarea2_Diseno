package Modelo;

import java.util.ArrayList;
import java.util.Date;

public class Ticket {
    private Date fecha;
    private int clienteID;
    public GradoImportancia categoria;
    public EstadoTicket estado;
    public String asunto;
    public int id;
    private Cliente cliente;
    public static ArrayList<Ticket> tickets = new ArrayList<>();

    public Ticket(String asunto, Cliente cliente){
        this.cliente = cliente;
        this.id = 0; //TODO Sacar el ID de la base de datos e insertar el nuevo ticket a la base
        this.categoria = GradoImportancia.SIN_CATALOGAR;
        this.estado = EstadoTicket.ATENDIDO.SIN_ATENDER;
        this.fecha = new Date();
        this.asunto = asunto;
        this.clienteID = cliente.ID;
        tickets.add(this);
    }

    public void setCategoria(GradoImportancia categoria) {
        this.categoria = categoria;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }
}
