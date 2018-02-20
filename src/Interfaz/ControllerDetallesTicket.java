package Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDetallesTicket implements Initializable {

    @FXML
    public Label nombreCliente;
    @FXML
    public Label fecha;
    @FXML
    public ContextMenu categoria;
    @FXML
    public Label estado;
    @FXML
    public Label asunto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
