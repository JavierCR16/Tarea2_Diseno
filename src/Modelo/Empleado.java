package Modelo;

import Gestores.GestorBD;
import javafx.beans.property.SimpleStringProperty;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Empleado {

    public SimpleStringProperty nombre;
    public SimpleStringProperty ID;
    public GradoImportancia especializacion;
    public Ticket atendiendo = null;
    public static ArrayList<Empleado> empleados = new ArrayList<>();
    public GestorBD gestor;

    public Empleado(String nombre, String id){
        this.nombre = new SimpleStringProperty(nombre);
        this.ID = new SimpleStringProperty(id);
        empleados.add(this);
    }

    public int solicitarTicket(){
        Ticket t = gestor.solicitarTicket(Integer.valueOf(this.getID()));
        if (t == null){
            return 1;
        }
        else{
            t.empleado = this;
            this.atendiendo = t;
        }
        return 0;
    }

    public void atenderTicket(){
       gestor.atenderTicket(Integer.valueOf(atendiendo.getId()), EstadoTicket.ATENDIDO);
       this.atendiendo = null;
    }

    public static ArrayList<TablaPorcentajeAtencion> getPorcentAtencionXEmpleado(GestorBD gestorBase) {
        return gestorBase.getPorcentAtencionXEmpleado();
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getID() {
        return ID.get();
    }

}
