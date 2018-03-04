package Controladores;

import Gestores.GestorBD;
import Modelo.Empleado;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Javier on 2/20/2018.
 */
public class ControladorVentanaEmpleado implements Initializable {

    @FXML
    public Button solicitar;
    @FXML
    public Button atender;
    @FXML
    public TextArea asunto;
    @FXML
    public Label idTicket;
    GestorBD gestorBDEmpleado;
    Empleado empleadoLogueado;

    public void initialize(URL fxmlLocations, ResourceBundle resources) {
        asunto.setEditable(false);
        solicitar.setOnAction(event -> {
            int ret = empleadoLogueado.solicitarTicket();
            if (ret == 0)
                iniciar();
            else {
                if (ret == 1)
                    gestorBDEmpleado.invocarAlerta("No hay tickets disponibles con su especializacion");
            }
        });
        atender.setOnAction(event -> {
            empleadoLogueado.atenderTicket();
            iniciar();
        });
    }

    public void iniciar() {
        asunto.clear();
        int id = Integer.valueOf(this.empleadoLogueado.getID());
        this.empleadoLogueado.atendiendo = gestorBDEmpleado.getTicketAtendiendo(id);
        if (this.empleadoLogueado.atendiendo == null) {
            idTicket.setText("");
            atender.setDisable(true);
            solicitar.setDisable(false);
        } else {
            idTicket.setText(empleadoLogueado.atendiendo.getId());
            asunto.setText(empleadoLogueado.atendiendo.getAsunto());
            atender.setDisable(false);
            solicitar.setDisable(true);
        }
    }

}
