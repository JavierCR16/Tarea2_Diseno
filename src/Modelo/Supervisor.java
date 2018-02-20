package Modelo;

import java.util.ArrayList;
import java.util.Date;

public class Supervisor {
    public String ID;
    //TODO Sacar los datos de tickets y empleados de la base;
    public ArrayList<Ticket> tickets = new ArrayList<>();
    public ArrayList<Empleado> empleados = new ArrayList<>();

    public void CatalogarTicket(int idTicket, GradoImportancia grado){
        for (Ticket ticket : tickets) {
            if (ticket.id == idTicket){
                ticket.setCategoria(grado);
                //TODO Actualizar la base con los datos del ticket
                break;
            }
        }
    }

    public void especializarEmpleado(int idEmpleado, GradoImportancia especializacion){
        for (Empleado empleado : empleados) {
            if (empleado.ID == idEmpleado){
               empleado.setEspecializacion(especializacion);
               //TODO Actualizar la base con los datos del empleado
                break;
            }
        }
    }

    public void agregarEmpleado(String nombre){
        Empleado nuevo = new Empleado(nombre);
        //TODO meter al nuevo empleado a la base
    }

    public void agregarCliente(String nombre){
        Cliente nuevo = new Cliente(nombre);
        //TODO meter al nuevo cliente a la base
    }

    //Funciones para estadisticas
    public int[] getDistribTicketsXFecha(Date fechaIni, Date fechaFin){
        String query =
                "SELECT COUNT(*) FROM Tickets WHERE fecha BETWEEN " +
                fechaIni.toString() + ", " + fechaFin.toString() + "GROUP BY categoria;";
        //TODO Hacer bien el query y convertir el resultado a tablas
        return new int[]{0, 0, 0};//TODO cambiar valores por resultado del query
    }

    public String getCategoriaMasRecibida(){
        String query = "SELECT categoria, COUNT(*) AS cantidad FROM Tickets WHERE categoria != \"SIN_CATALOGAR\" " +
                "GROUP BY categoria ORDER BY cantidad LIMIT 1;";
        //TODO Revisar query y cambiar resultado de funcion
        return "SIN_CATALOGAR";
    }

    public void getPorcentAtencionXEmpleado(){
        String query = "SELECT empleado, COUNT(*)/100 AS porcentaje FROM Tickets GROUP BY empleado ORDER BY porcentaje;";
        //TODO Revisar query y convertir resultado a Observable list
    }
}
