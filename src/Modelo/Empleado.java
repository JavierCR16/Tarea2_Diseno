package Modelo;

import java.util.ArrayList;

public class Empleado {
    public String nombre;
    public int ID;
    public GradoImportancia especializacion;
    public Ticket atendiendo;
    //FIXME DUDA CON JAVI
    public static ArrayList<Empleado> empleados = new ArrayList<>();

    public Empleado(String nombre, int id){
        this.nombre = nombre;
        this.ID = id; //TODO Sacar el ID  de la base e insertar al nuevo empleado
        empleados.add(this);
    }

    public void solicitarTicket(){
        String query = "SELECT * FROM Ticket WHERE categoria = " + especializacion.toString() + "LIMIT 1;"; //TODO Hacer bien el query y vandarlo a la base
        atendiendo = new Ticket("", null); //TODO transformar el resultado del query en Ticket para asignarselo al empleado
        atendiendo.setEstado(EstadoTicket.ATENDIDO.ATENDIENDO);
        //TODO actualizar la base para cambier el estado del Ticket
    }

    public void atenderTicket(){
        atendiendo.setEstado(EstadoTicket.ATENDIDO);
        //TODO Actualizar la base para poner el nuevo ticket como atendido
        atendiendo = null;
    }

    public void setEspecializacion(GradoImportancia especializacion) {
        this.especializacion = especializacion;
    }

    public void getPorcentAtencionXEmpleado() {
        String query = "SELECT empleado, COUNT(*)/100 AS porcentaje FROM Tickets GROUP BY empleado ORDER BY porcentaje;";
        //TODO Revisar query y convertir resultado a Observable list
    }
}
