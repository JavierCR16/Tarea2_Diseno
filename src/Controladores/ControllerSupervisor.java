package Controladores;

import Controladores.ControllerDetallesTicket;
import Gestores.GestorBD;
import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.Supervisor;
import Modelo.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    public Button actualizarTickets;
    @FXML
    public Button actualizarEmpleados;
    @FXML
    public Button actualizarClientes;
    @FXML
    public TableView tablaEmpleados;
    @FXML
    public TableView listTickets;
    @FXML
    public TableView listClientes;
    @FXML
    public TableColumn columnaIdEmpleado;
    @FXML
    public TableColumn columnaNombreEmpleado;
    @FXML
    public TableColumn columnaIdTicket;
    @FXML
    public TableColumn columnaNombreCliente;
    @FXML
    public TableColumn columnaAsunto;
    @FXML
    public TableColumn columnaIdCliente;



    GestorBD gestorBDSupervisor;

    Supervisor supervisorLogueado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColumnas();


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
        detallesCliente.setOnAction(event -> {
            abrirDetalles(true, new Cliente("Bryan", 0));
        });
        detallesEmpleado.setOnAction(event -> {
            abrirDetalles(false, new Empleado("Bryan", "0"));
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

    public void abrirDetalles(Boolean caso, Object persona){
        //True cliente False empleado
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        ControllerEdit c = null;
        try {
            root = loader.load(getClass().getResource("../Interfaz/editPersona.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        c = loader.getController();
        Stage escenario = new Stage();
        if(caso){
            Cliente cliente1 = (Cliente) persona;
            escenario.setTitle("Detalles Cliente");
            c.idO = String.valueOf(cliente1.ID);
            c.nombreO = cliente1.getNombre();
            c.caso = caso;
        }
        else{
            Empleado empleado = (Empleado) persona;
            escenario.setTitle("Detalles Empleado");
            c.idO = String.valueOf(empleado.ID);
            c.nombreO = empleado.getNombre();
            c.caso = caso;
        }
        c.iniciar();
        escenario.setScene(new Scene(root, 300, 215));
        escenario.show();
    }


    public void cargarDatosDefecto(){
        ArrayList<Empleado> empleadosSinEspecializar = gestorBDSupervisor.getEmpleadosSinEspecializar();
      //  ArrayList<Ticket> ticketsSinCategorizar = gestorBDSupervisor.getTicketsSinCategorizar();
      //  ArrayList<Cliente> clientes = gestorBDSupervisor.getClientes();
        ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList(empleadosSinEspecializar);
        tablaEmpleados.setItems(listaEmpleados);
    }

    public void configurarColumnas(){
        columnaIdEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("ID"));
        columnaNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombre"));
    }
}
