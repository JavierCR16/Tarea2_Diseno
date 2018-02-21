package Controladores;

import Controladores.ControllerDetallesTicket;
import Gestores.GestorBD;
import Modelo.Supervisor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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

    GestorBD gestorBDSupervisor;

    Supervisor supervisorLogueado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Cargar datos de la base en los listView de clientes, empleados y tickets
        detallesTicket.setOnAction(event -> {
            //Ticket selected = (Ticket) listTickets.getSelectionModel().getSelectedItem();

        });
        agregarCliente.setOnAction(event -> {
            abrirAgregar("Cliente");
        });
        agregarEmpleado.setOnAction(event -> {
            abrirAgregar("Empleado");
        });
    }

    public void abrirAgregar(String option){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../Interfaz/Agregar.fxml").openStream());
            ControllerAgregar controllerAgregar = loader.getController();

            controllerAgregar.caso = option;
            controllerAgregar.supervisorActual = supervisorLogueado; //Se manda a la otra ventana para poder enviar el id al gestorBD para que el empleado pueda tener una referencia de su supervisor en la base de datos.
            controllerAgregar.supervisorActual.setGestorSupervisor(gestorBDSupervisor);

            Stage escenario = new Stage();
            escenario.setTitle("Agregar "+ option);
            escenario.setScene(new Scene(root, 273, 146));
            escenario.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
