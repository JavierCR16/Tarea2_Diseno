package Controladores;

import Modelo.Supervisor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorAgregar implements Initializable {
    @FXML
    public Button agregar;
    @FXML
    public TextField nombreField;

    public String caso;

    public Supervisor supervisorActual;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        agregar.setOnAction(event -> {
            String nombre = nombreField.getText();
            switch (caso) {
                case "Cliente":
                    supervisorActual.agregarCliente(nombre); //Aqui el ID del supervisor no va a ser necesario, pero como la funcion esta dividida por un case, entonces igual hay que enviarlo.
                    break;
                case "Empleado":
                    supervisorActual.agregarEmpleado(nombre);
                    break;
            }
            Stage actual = (Stage) agregar.getScene().getWindow();
            actual.close();
        });
    }
}
