package Controladores;

import Controladores.ControllerDetallesTicket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSupervisor implements Initializable {
    @FXML
    public Button detallesTicket;
    @FXML
    public Button detallesEmpleado;
    @FXML
    public Button detallesCliente;
    @FXML
    public Button agregarEmpleado;
    @FXML
    public Button agregarCliente;
    @FXML
    public ListView listTickets;
    @FXML
    public ListView listEmpleados;
    @FXML
    public ListView listClientes;
    @FXML
    public TableView ticketXcateg;
    @FXML
    public DatePicker fechaIni;
    @FXML
    public DatePicker fechaFin;
    @FXML
    public Label masRecibidos;
    @FXML
    public ListView perctPorEmpleado;
    @FXML
    public Button actualizar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Cargar datos de la base en los listView de clientes, empleados y tickets
        detallesTicket.setOnAction(event -> {
            //Ticket selected = (Ticket) listTickets.getSelectionModel().getSelectedItem();
            try {
                Stage escenario = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("interfaz/DetallesTicket.fxml").openStream());
                ControllerDetallesTicket controladorJuego = loader.getController();
                escenario.setScene(new Scene(root,1000,780));
                escenario.setTitle("Kakuro");
                escenario.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

    }
}
