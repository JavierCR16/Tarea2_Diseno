package Controladores;

import Modelo.GradoImportancia;
import Modelo.Supervisor;
import Modelo.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDetallesTicket implements Initializable {

    @FXML
    public Label nombreCliente;
    @FXML
    public Label fecha;
    @FXML
    public ComboBox comboBox;
    @FXML
    public Label estado;
    @FXML
    public Label asunto;
    @FXML
    public Button save;

    public Ticket ticket;
    public Supervisor supervisor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().addAll(GradoImportancia.values());
        save.setOnAction(event -> {
            GradoImportancia g = (GradoImportancia) comboBox.getValue();
            supervisor.gestorSupervisor.actualizarTicket(Integer.valueOf(ticket.getId()), g.toString());// TODO El supervisor deberia tener uan funcion de especializar ticket que llama al gestor y haga el brete, sino no se estaria respetando el diseño.
            Stage actual = (Stage) save.getScene().getWindow();
            actual.close();
        });
    }

    public void iniciar(){
        fecha.setText(ticket.fecha.toString());
        nombreCliente.setText(ticket.cliente.getNombre());
        estado.setText(ticket.estado.toString());
        asunto.setText(ticket.getAsunto());
    }
}
