package Controladores;

import Gestores.GestorBD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladorVentanaLogin implements Initializable {

    @FXML
    TextField idSupervisor;

    @FXML
    TextField idEmpleado;

    @FXML
    TextField idCliente;

    @FXML
    TextField nombreSupervisor;

    @FXML
    TextField nombreEmpleado;

    @FXML
    TextField nombreCliente;

    @FXML
    Button ingresarSupervisor;

    @FXML
    Button ingresarEmpleado;

    @FXML
    Button ingresarCliente;

    GestorBD gestorBase = new GestorBD();

    public void initialize(URL fxmlLocations, ResourceBundle resources) {

        ingresarSupervisor.setOnAction(event -> {


        });

        ingresarEmpleado.setOnAction(event -> {


        });

        ingresarCliente.setOnAction(event -> {


        });


    }

    public void loguearSupervisor(String nombre, int id) {


        if (gestorBase.existeConexionUsuarios(nombre, String.valueOf(id)) && gestorBase.existeSupervisor(nombre, id)) {
            abrirVentanaSupervisor();
        }
        else{
            gestorBase.invocarAlerta("No existe un usuario asociado a: "+nombre);
        }


    }

    public void abrirVentanaSupervisor() {

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../Interfaz/VentanaSupervisor").openStream());
            ControladorVentanaSupervisor controlador = loader.getController();
            controlador.gestorBDSupervisor = gestorBase;
            Stage escenario = new Stage();
            escenario.setTitle("Participante");
            escenario.setScene(new Scene(root, 1053, 417));
            escenario.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
