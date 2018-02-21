package Controladores;

import Gestores.GestorBD;
import Modelo.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Javier on 2/20/2018.
 */
public class ControladorVentanaCliente implements Initializable {

    @FXML
    public TextField asuntoField;
    @FXML
    public Button guardar;
    GestorBD gestorBDCliente;
    Cliente clienteLogueado;

    public void initialize(URL fxmlLocations, ResourceBundle resources){
        guardar.setOnAction(event -> {
            clienteLogueado.agregarTicket(asuntoField.getText());
        });

    }

}
