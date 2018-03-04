package Modelo;

import Gestores.GestorBD;

public class Supervisor {

    private int ID;

    private String nombre;

    public GestorBD gestorSupervisor;

    public Supervisor(String nombre, int ID) {
        this.nombre = nombre;
        this.ID = ID;
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

}
