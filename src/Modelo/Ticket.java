package Modelo;

import Gestores.GestorBD;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Date;

public class Ticket {
    public Date fecha;
    public GradoImportancia categoria;
    public EstadoTicket estado;
    public SimpleStringProperty asunto;
    public SimpleStringProperty id;
    public Cliente cliente;
    private Empleado empleado;
    public static ArrayList<Ticket> tickets = new ArrayList<>();

    public Ticket(String asunto, Cliente cliente){
        this.id = new SimpleStringProperty();
        this.cliente = cliente;//TODO Sacar el ID de la base de datos e insertar el nuevo ticket a la base
        this.categoria = GradoImportancia.SIN_CATALOGAR;
        this.estado = EstadoTicket.ATENDIDO.SIN_ATENDER;
        this.fecha = new Date();
        this.asunto = new SimpleStringProperty(asunto);
        tickets.add(this);
    }

    public Ticket(String asunto, String id){
        this.id = new SimpleStringProperty(id);
        this.asunto = new SimpleStringProperty(asunto);
    }

    public void setCategoria(GradoImportancia categoria) {
        this.categoria = categoria;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public static TablaTickets getDistribTicketsXFecha(String fechaIni, String fechaFin, GestorBD gestorBase) {

        return gestorBase.getDistTicketsXFecha(fechaIni,fechaFin);
    }

    public static String getCategoriaMasRecibida(GestorBD gestorBase) {

        return gestorBase.getTipoTicketsMasRecibidos();
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public GradoImportancia getCategoria() {
        return categoria;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public String getAsunto() {
        return asunto.get();
    }

    public SimpleStringProperty asuntoProperty() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto.set(asunto);
    }

    public String getId() {
        return id.get();
    }


    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }






}
