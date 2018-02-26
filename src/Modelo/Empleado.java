package Modelo;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Empleado {
    public SimpleStringProperty nombre;
    public SimpleStringProperty ID;
    public GradoImportancia especializacion;
    public Ticket atendiendo;
    public static ArrayList<Empleado> empleados = new ArrayList<>();

    public Empleado(String nombre, String id){
        this.nombre = new SimpleStringProperty(nombre);
        this.ID = new SimpleStringProperty(id); //TODO Sacar el ID  de la base e insertar al nuevo empleado
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

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getID() {
        return ID.get();
    }

    public SimpleStringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

}
