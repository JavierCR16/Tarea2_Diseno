package Modelo;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Javier on 3/4/2018.
 */
public class TablaTickets {

    SimpleStringProperty cantidadAmarillo;
    SimpleStringProperty cantidadRojo;
    SimpleStringProperty cantidadVerde;

    public TablaTickets(String cantAmarillo, String cantRojo, String cantVerde){
        this.cantidadAmarillo = new SimpleStringProperty(cantAmarillo);
        this.cantidadRojo = new SimpleStringProperty(cantRojo);
        this.cantidadVerde = new SimpleStringProperty(cantVerde);
    }


    public String getCantidadAmarillo() {
        return cantidadAmarillo.get();
    }

    public SimpleStringProperty cantidadAmarilloProperty() {
        return cantidadAmarillo;
    }

    public void setCantidadAmarillo(String cantidadAmarillo) {
        this.cantidadAmarillo.set(cantidadAmarillo);
    }

    public String getCantidadRojo() {
        return cantidadRojo.get();
    }

    public SimpleStringProperty cantidadRojoProperty() {
        return cantidadRojo;
    }

    public void setCantidadRojo(String cantidadRojo) {
        this.cantidadRojo.set(cantidadRojo);
    }

    public String getCantidadVerde() {
        return cantidadVerde.get();
    }

    public SimpleStringProperty cantidadVerdeProperty() {
        return cantidadVerde;
    }

    public void setCantidadVerde(String cantidadVerde) {
        this.cantidadVerde.set(cantidadVerde);
    }

}
