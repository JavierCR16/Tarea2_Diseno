package Modelo;

import Gestores.GestorBD;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Date;

public class Ticket {
    public Date fecha;
    public GradoImportancia categoria;
    private SimpleStringProperty categoriaAux;
    private SimpleStringProperty idEmpleado;
    public EstadoTicket estado;
    public SimpleStringProperty asunto;
    public SimpleStringProperty id;
    public Cliente cliente;
    public Empleado empleado;
    public static ArrayList<Ticket> tickets = new ArrayList<>();

    public Ticket(String asunto, Cliente cliente){
        this.id = new SimpleStringProperty();
        this.cliente = cliente;
        this.categoria = GradoImportancia.SIN_CATALOGAR;
        this.estado = EstadoTicket.SIN_ATENDER;
        this.fecha = new Date();
        this.asunto = new SimpleStringProperty(asunto);
        tickets.add(this);
    }

    public Ticket(String asunto, String id){
        //Este constructor se usa para pasarle los datos al empleado en su interfaz
        this.id = new SimpleStringProperty(id);
        this.asunto = new SimpleStringProperty(asunto);
    }

    public Ticket(String id, String idEmpleado, String categoria){
        this.id = new SimpleStringProperty(id);
        this.idEmpleado = new SimpleStringProperty(idEmpleado);
        this.categoriaAux = new SimpleStringProperty(categoria);
    }

    public static TablaTickets getDistribTicketsXFecha(String fechaIni, String fechaFin, GestorBD gestorBase) {

        return gestorBase.getDistTicketsXFecha(fechaIni,fechaFin);
    }

    public static String getCategoriaMasRecibida(GestorBD gestorBase) {

        return gestorBase.getTipoTicketsMasRecibidos();
    }

    public String getAsunto() {
        return asunto.get();
    }

    public String getId() {
        return id.get();
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

    public String getCategoriaAux() {
        return categoriaAux.get();
    }

    public SimpleStringProperty categoriaAuxProperty() {
        return categoriaAux;
    }

    public void setCategoriaAux(String categoriaAux) {
        this.categoriaAux.set(categoriaAux);
    }

    public String getIdEmpleado() {
        return idEmpleado.get();
    }

    public SimpleStringProperty idEmpleadoProperty() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado.set(idEmpleado);
    }

}
