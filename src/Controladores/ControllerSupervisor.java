package Controladores;

import Controladores.ControllerDetallesTicket;
import Gestores.GestorBD;
import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.Supervisor;
import Modelo.Ticket;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

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
    public TableColumn columnaNombreClienteTicket;
    @FXML
    public TableColumn columnaAsunto;
    @FXML
    public TableColumn columnaIdCliente;
    @FXML
    public TableColumn columnaNombreCliente;

    public GestorBD gestorBDSupervisor;

    public Supervisor supervisorLogueado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColumnas();


        //TODO Cargar datos de la base en los listView de clientes, empleados y tickets
        detallesTicket.setOnAction(event -> {


        });

        actualizarEmpleados.setOnAction(event->{

            actualizarInformacion(1);

        });
        actualizarTickets.setOnAction(event->{
            actualizarInformacion(2);
        });
        actualizarClientes.setOnAction(event ->{

            actualizarInformacion(3);
        });

        agregarCliente.setOnAction(event -> {
            abrirAgregar("Cliente");
        });
        agregarEmpleado.setOnAction(event -> {
            abrirAgregar("Empleado");
        });
        detallesCliente.setOnAction(event -> {
            abrirDetalles(true, (Cliente) listClientes.getSelectionModel().getSelectedItem());
        });
        detallesEmpleado.setOnAction(event -> {
            Empleado e = (Empleado) tablaEmpleados.getSelectionModel().getSelectedItem();
            abrirDetalles(false, e);
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
            c.idO = cliente1.getID();
            c.nombreO = cliente1.getNombre();
            c.caso = caso;
        }
        else{
            Empleado empleado = (Empleado) persona;
            escenario.setTitle("Detalles Empleado");
            c.idO = empleado.getID();
            c.nombreO = empleado.getNombre();
            c.caso = caso;
        }
        c.iniciar(this.supervisorLogueado);
        escenario.setScene(new Scene(root, 300, 215));
        escenario.show();
    }


    public void cargarDatosDefecto(){
        ArrayList<Empleado> empleadosSinEspecializar = gestorBDSupervisor.getEmpleadosSinEspecializar();
        ArrayList<Ticket> ticketsSinCategorizar = gestorBDSupervisor.getTicketsSinCategorizar();
        ArrayList<Cliente> clientes = gestorBDSupervisor.getClientes();

        ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList(empleadosSinEspecializar);
        ObservableList<Cliente> listaCliente = FXCollections.observableArrayList(clientes);
        ObservableList<Ticket> listaTickets = FXCollections.observableArrayList(ticketsSinCategorizar);

        tablaEmpleados.setItems(listaEmpleados);
        listClientes.setItems(listaCliente);
        listTickets.setItems(listaTickets);

    }

    public void actualizarInformacion(int opcion){

        switch(opcion){
            case 1:
                ArrayList<Empleado> empleados = gestorBDSupervisor.getEmpleadosSinEspecializar();

                ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList(empleados);
                tablaEmpleados.setItems(listaEmpleados);
                break;
            case 2:

                  ArrayList<Ticket> ticketsSinCategorizar = gestorBDSupervisor.getTicketsSinCategorizar();

                ObservableList<Ticket> listaTickets = FXCollections.observableArrayList(ticketsSinCategorizar);
                listTickets.setItems(listaTickets);
                break;
            case 3:

                ArrayList<Cliente> clientes = gestorBDSupervisor.getClientes();
                ObservableList<Cliente> listaClientes= FXCollections.observableArrayList(clientes);
                listClientes.setItems(listaClientes);
                break;

        }

    }

    public void configurarColumnas(){
        columnaIdEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("ID"));
        columnaNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombre"));
        columnaIdCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("ID"));
        columnaNombreCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombre"));
        columnaIdTicket.setCellValueFactory(new PropertyValueFactory<Ticket,String>("id"));
        columnaNombreClienteTicket.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ticket, String> t) {
                return new SimpleStringProperty(t.getValue().getCliente().getNombre());
            }
        });
        columnaAsunto.setCellValueFactory(new PropertyValueFactory<Ticket,String>("asunto"));
    }
}
