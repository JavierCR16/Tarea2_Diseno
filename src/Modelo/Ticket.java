package Modelo;

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

    public void setCategoria(GradoImportancia categoria) {
        this.categoria = categoria;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public int[] getDistribTicketsXFecha(Date fechaIni, Date fechaFin) {
        String query =
                "SELECT COUNT(*) FROM Tickets WHERE fecha BETWEEN " +
                        fechaIni.toString() + ", " + fechaFin.toString() + "GROUP BY categoria;";
        //TODO Hacer bien el query y convertir el resultado a tablas
        return new int[]{0, 0, 0};//TODO cambiar valores por resultado del query
    }

    public String getCategoriaMasRecibida() {
        String query = "SELECT categoria, COUNT(*) AS cantidad FROM Tickets WHERE categoria != \"SIN_CATALOGAR\" " +
                "GROUP BY categoria ORDER BY cantidad LIMIT 1;";
        //TODO Revisar query y cambiar resultado de funcion
        return "SIN_CATALOGAR";
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
