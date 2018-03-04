package Modelo;

import Gestores.GestorBD;

import java.util.ArrayList;

public class Supervisor {

    private int ID;

    private String nombre;

    ArrayList<Empleado> empleadosSinEspecializar;

    ArrayList<Ticket> ticketsSinCategorizar;

    public GestorBD gestorSupervisor;

    public Supervisor(String nombre, int ID) {
        this.nombre = nombre;
        this.ID = ID;
        empleadosSinEspecializar = new ArrayList();
        ticketsSinCategorizar = new ArrayList<>();
    }

    public void catalogarTicket(int idTicket, String grado) {
        gestorSupervisor.actualizarTicket(idTicket, grado);
    }

    public void especializarEmpleado(int idEmpleado, String especializacion) {
        gestorSupervisor.actualizarEmpleado(idEmpleado, especializacion);
    }

    public void agregarEmpleado(String nombre) {
        gestorSupervisor.agregarClienteEmpleado(nombre, "Empleado",ID);
    }

    public void agregarCliente(String nombre) {
        gestorSupervisor.agregarClienteEmpleado(nombre, "Cliente", ID);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGestorSupervisor(GestorBD gestorSupervisor) {
        this.gestorSupervisor = gestorSupervisor;
    }

    public ArrayList<Empleado> getEmpleadosSinEspecializar() {
        setEmpleadosSinEspecializar();
        return empleadosSinEspecializar;
    }

    public void setEmpleadosSinEspecializar() {
        this.empleadosSinEspecializar = gestorSupervisor.getEmpleadosSinEspecializar();
    }

    public ArrayList<Ticket> getTicketsSinCategorizar() {
        setTicketsSinCategorizar();
        return ticketsSinCategorizar;
    }

    public void setTicketsSinCategorizar() {
        this.ticketsSinCategorizar = gestorSupervisor.getTicketsSinCategorizar();
    }
}
