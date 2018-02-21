package Controladores;

import Gestores.GestorBD;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAgregar implements Initializable {
    @FXML
    public Button agregar;
    @FXML
    public TextField nombreField;

    public GestorBD gestorBD;

    public String caso;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        agregar.setOnAction(event -> {
            String nombre = nombreField.getText();
            switch (caso){
                case "Cliente":
                    gestorBD.agregarCliente(nombre, "Cliente");
                case "Empleado":
                    gestorBD.agregarCliente(nombre, "Empleado");

            }

        });
    }
}
