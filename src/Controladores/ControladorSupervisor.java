package Controladores;

import Gestores.GestorBD;
import Modelo.*;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ControladorSupervisor implements Initializable {
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
    public TableView perctPorEmpleado;
    @FXML
    public TableColumn columnaCategoriaPorcentaje;
    @FXML
    public TableColumn columnaNombrePorcentaje;
    @FXML
    public TableColumn columnaPorcentaje;
    @FXML
    public TableColumn columnaAmarillo;
    @FXML
    public TableColumn columnaRojo;
    @FXML
    public TableColumn columnaVerde;
    @FXML
    public Button actualizarEstadisticas;
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
    public TableView enAtencionList;
    @FXML
    public TableView atendidosList;
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
    @FXML
    public TableColumn idTicket1;
    @FXML
    public TableColumn idTicket2;
    @FXML
    public TableColumn idEmpleado1;
    @FXML
    public TableColumn idEmpleado2;
    @FXML
    public TableColumn categoria1;
    @FXML
    public TableColumn categoria2;

    public GestorBD gestorBDSupervisor;

    public Supervisor supervisorLogueado;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColumnas();
        actualizarEstadisticas.setOnAction(event -> {
            mostrarEstadisticas();
        });
        detallesTicket.setOnAction(event -> {
            Ticket actual = (Ticket) listTickets.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("../Interfaz/DetallesTicket.fxml").openStream());
                ControladorDetallesTicket controller = loader.getController();
                controller.ticket = actual;
                controller.supervisor = this.supervisorLogueado;
                controller.iniciar();
                Stage escenario = new Stage();
                escenario.setTitle("Detalles Ticket");
                escenario.setScene(new Scene(root, 330, 215));
                escenario.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        actualizarEmpleados.setOnAction(event -> {
            actualizarInformacion(1);
        });
        actualizarTickets.setOnAction(event -> {
            actualizarInformacion(2);
        });
        actualizarClientes.setOnAction(event -> {
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

    public void abrirAgregar(String option) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../Interfaz/Agregar.fxml").openStream());
            ControladorAgregar controladorAgregar = loader.getController();

            controladorAgregar.caso = option;
            controladorAgregar.supervisorActual = supervisorLogueado; //Se manda a la otra ventana para poder enviar el id al gestorBD para que el empleado pueda tener una referencia de su supervisor en la base de datos.
            controladorAgregar.supervisorActual.setGestorSupervisor(gestorBDSupervisor);

            Stage escenario = new Stage();
            escenario.setTitle("Agregar " + option);
            escenario.setScene(new Scene(root, 273, 146));
            escenario.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirDetalles(Boolean caso, Object persona) {
        //True cliente False empleado
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        ControladorEdit c = null;
        try {
            root = loader.load(getClass().getResource("../Interfaz/editPersona.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        c = loader.getController();
        Stage escenario = new Stage();
        if (caso) {
            Cliente cliente1 = (Cliente) persona;
            escenario.setTitle("Detalles Cliente");
            c.idO = cliente1.getID();
            c.nombreO = cliente1.getNombre();
            c.caso = caso;
        } else {
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

    public void cargarDatosDefecto() {
        supervisorLogueado.setGestorSupervisor(gestorBDSupervisor);

        ArrayList<Empleado> empleadosSinEspecializar = supervisorLogueado.getEmpleadosSinEspecializar();
        ArrayList<Ticket> ticketsSinCategorizar = supervisorLogueado.getTicketsSinCategorizar();
        ArrayList<Cliente> clientes = gestorBDSupervisor.getClientes();
        ArrayList<Ticket> ticketsAtendidos = gestorBDSupervisor.getTicketsXempleado(true);
        ArrayList<Ticket> ticketsEnAtencion = gestorBDSupervisor.getTicketsXempleado(false);

        ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList(empleadosSinEspecializar);
        ObservableList<Cliente> listaCliente = FXCollections.observableArrayList(clientes);
        ObservableList<Ticket> listaTickets = FXCollections.observableArrayList(ticketsSinCategorizar);
        ObservableList<Ticket> atendidos = FXCollections.observableArrayList(ticketsAtendidos);
        ObservableList<Ticket> enAtencion = FXCollections.observableArrayList(ticketsEnAtencion);

        tablaEmpleados.setItems(listaEmpleados);
        listClientes.setItems(listaCliente);
        listTickets.setItems(listaTickets);
        atendidosList.setItems(atendidos);
        enAtencionList.setItems(enAtencion);

    }

    public void actualizarInformacion(int opcion) {

        switch (opcion) {
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
                ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(clientes);
                listClientes.setItems(listaClientes);
                break;

        }

    }

    public void configurarColumnas() {
        //Agregado para tab de tickets
        idEmpleado2.setCellValueFactory(new PropertyValueFactory<Ticket, String>("idEmpleado"));
        idEmpleado2.setCellValueFactory(new PropertyValueFactory<Ticket, String>("idEmpleado"));
        idTicket1.setCellValueFactory(new PropertyValueFactory<Ticket, String>("id"));
        idTicket2.setCellValueFactory(new PropertyValueFactory<Ticket, String>("id"));
        categoria1.setCellValueFactory(new PropertyValueFactory<Ticket, String>("categoriaAux"));
        categoria2.setCellValueFactory(new PropertyValueFactory<Ticket, String>("categoriaAux"));
        //
        columnaIdEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("ID"));
        columnaNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        columnaIdCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ID"));
        columnaNombreCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        columnaIdTicket.setCellValueFactory(new PropertyValueFactory<Ticket, String>("id"));
        columnaNombreClienteTicket.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>) t ->
                new SimpleStringProperty(t.getValue().getCliente().getNombre()));
        columnaAsunto.setCellValueFactory(new PropertyValueFactory<String, String>("asunto"));
        columnaAmarillo.setCellValueFactory(new PropertyValueFactory<TablaTickets, String>("cantidadAmarillo"));
        columnaRojo.setCellValueFactory(new PropertyValueFactory<TablaTickets, String>("cantidadRojo"));
        columnaVerde.setCellValueFactory(new PropertyValueFactory<TablaTickets, String>("cantidadVerde"));
        columnaCategoriaPorcentaje.setCellValueFactory(new PropertyValueFactory<TablaPorcentajeAtencion, String>("categoria"));
        columnaNombrePorcentaje.setCellValueFactory(new PropertyValueFactory<TablaTickets, String>("nombreEmpleado"));
        columnaPorcentaje.setCellValueFactory(new PropertyValueFactory<TablaTickets, String>("porcentajeAtencion"));
    }

    public void mostrarEstadisticas() {
        ticketXcateg.getItems().clear();
        masRecibidos.setText(Ticket.getCategoriaMasRecibida(gestorBDSupervisor)); //Set el ticket mas recibido
        TablaTickets cantTicketsXFecha = null;
        try {
            cantTicketsXFecha = Ticket.getDistribTicketsXFecha(fechaIni.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    fechaFin.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), gestorBDSupervisor);
        }
        catch(NullPointerException e){
            gestorBDSupervisor.invocarAlerta("Ingrese una fecha valida");
        }
        TablaTickets[] informacionCantidades = {cantTicketsXFecha}; //Set la cantidad de tickets por fechas seleccionadas
        ticketXcateg.setItems(FXCollections.observableArrayList(Arrays.asList(informacionCantidades)));
        ArrayList<TablaPorcentajeAtencion> porcentajesEmpleados = Empleado.getPorcentAtencionXEmpleado(gestorBDSupervisor);
        ticketXcateg.setItems(FXCollections.observableArrayList(Arrays.asList(informacionCantidades)));
        perctPorEmpleado.setItems(FXCollections.observableArrayList(porcentajesEmpleados));
    }

}
