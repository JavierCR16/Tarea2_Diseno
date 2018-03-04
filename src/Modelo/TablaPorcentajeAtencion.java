package Modelo;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Javier on 3/4/2018.
 */
public class TablaPorcentajeAtencion {

    SimpleStringProperty categoria;
    SimpleStringProperty nombreEmpleado;
    SimpleStringProperty porcentajeAtencion;

    public TablaPorcentajeAtencion(String categoria, String nombreEmpleado, String porcentajeAtencion){
        this.categoria = new SimpleStringProperty(categoria);
        this.nombreEmpleado = new SimpleStringProperty(nombreEmpleado);
        this.porcentajeAtencion = new SimpleStringProperty(porcentajeAtencion);
    }


    public String getCategoria() {
        return categoria.get();
    }

    public SimpleStringProperty categoriaProperty() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria.set(categoria);
    }

    public String getNombreEmpleado() {
        return nombreEmpleado.get();
    }

    public SimpleStringProperty nombreEmpleadoProperty() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado.set(nombreEmpleado);
    }

    public String getPorcentajeAtencion() {
        return porcentajeAtencion.get();
    }

    public SimpleStringProperty porcentajeAtencionProperty() {
        return porcentajeAtencion;
    }

    public void setPorcentajeAtencion(String porcentajeAtencion) {
        this.porcentajeAtencion.set(porcentajeAtencion);
    }



}
