package Controladores;

import Gestores.GestorBD;
import Modelo.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Javier on 2/20/2018.
 */
public class ControladorVentanaCliente implements Initializable {

    @FXML
    public TextArea asuntoField;
    @FXML
    public Button guardar;

    GestorBD gestorBDCliente;
    Cliente clienteLogueado;

    public void initialize(URL fxmlLocations, ResourceBundle resources){
        guardar.setOnAction(event -> {
            boolean exito = clienteLogueado.agregarTicket(asuntoField.getText());
            if (exito){
                this.gestorBDCliente.invocarAlerta("El ticket fue enviado con exito");
                asuntoField.clear();
            }
            else{
                this.gestorBDCliente.invocarAlerta("Error en la base de datos");
            }
        });

    }

}
