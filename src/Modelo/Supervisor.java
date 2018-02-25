package Modelo;

import Gestores.GestorBD;

import java.util.ArrayList;
import java.util.Date;

public class Supervisor {

    private int ID;

    private String nombre;

    public GestorBD gestorSupervisor;

    public Supervisor(String nombre, int ID) {
        this.nombre = nombre;
        this.ID = ID;
    }

    public void catalogarTicket(int idTicket, GradoImportancia grado) {
        for (Ticket ticket : Ticket.tickets) {
            if (ticket.id == idTicket) {
                ticket.setCategoria(grado);
                //TODO Actualizar la base con los datos del ticket
                break;
            }
        }
    }

    public void especializarEmpleado(int idEmpleado, GradoImportancia especializacion) {
        for (Empleado empleado : Empleado.empleados) {
            if (empleado.ID == idEmpleado) {
                empleado.setEspecializacion(especializacion);
                //TODO Actualizar la base con los datos del empleado
                break;
            }
        }
    }

    public void agregarEmpleado(String nombre) {
        gestorSupervisor.agregarClienteEmpleado(nombre, "Empleado",ID);

    }

    public void agregarCliente(String nombre) {
        gestorSupervisor.agregarClienteEmpleado(nombre, "Cliente",ID);
    }

    //TODO Eliminar funciones de estadisticas

    /*Funciones para estadisticas
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

    public void getPorcentAtencionXEmpleado() {
        String query = "SELECT empleado, COUNT(*)/100 AS porcentaje FROM Tickets GROUP BY empleado ORDER BY porcentaje;";
        //TODO Revisar query y convertir resultado a Observable list
    }
    */

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public GestorBD getGestorSupervisor() {
        return gestorSupervisor;
    }

    public void setGestorSupervisor(GestorBD gestorSupervisor) {
        this.gestorSupervisor = gestorSupervisor;
    }

}
