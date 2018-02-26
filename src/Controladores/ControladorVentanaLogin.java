package Controladores;

import Gestores.GestorBD;
import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.Supervisor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorVentanaLogin implements Initializable {

    @FXML
    TextField idSupervisor;

    @FXML
    TextField idEmpleado;

    @FXML
    TextField idCliente;

    @FXML
    TextField nombreSupervisor;

    @FXML
    TextField nombreEmpleado;

    @FXML
    TextField nombreCliente;

    @FXML
    Button ingresarSupervisor;

    @FXML
    Button ingresarEmpleado;

    @FXML
    Button ingresarCliente;

    GestorBD gestorBase = new GestorBD();

    public void initialize(URL fxmlLocations, ResourceBundle resources) {

        ingresarSupervisor.setOnAction(event -> {

            String nombreSuper = nombreSupervisor.getText();
            int idSuper = Integer.valueOf(idSupervisor.getText());

            loguearEntidad(nombreSuper,idSuper,"Supervisor",600,400);

        });

        ingresarEmpleado.setOnAction(event -> {
            String nombreEmp = nombreEmpleado.getText();
            int idEmp = Integer.valueOf(idEmpleado.getText());
            loguearEntidad(nombreEmp,idEmp,"Empleado",1200,600);
        });



        ingresarCliente.setOnAction(event -> {
            String nombreCli = nombreCliente.getText();
            int idCli = Integer.valueOf(idCliente.getText());
            loguearEntidad(nombreCli,idCli,"Cliente",606,414);
        });


    }

    public void loguearEntidad(String nombre, int id , String nombreTabla, int width, int height) {


        if (gestorBase.existeConexionUsuarios(nombre, String.valueOf(id)) && gestorBase.existeEntidad(nombre, id,nombreTabla.toUpperCase())) {

            gestorBase.establecerConexionUsuario(nombre,String.valueOf(id));

            abrirVentanaEntidad(nombreTabla,width,height, nombre, id);
        }
        else{
            gestorBase.invocarAlerta("No existe un usuario asociado a: "+nombre);
        }


    }

    public void abrirVentanaEntidad(String entidad , int width, int height, String nombre, int id) {

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../Interfaz/"+entidad+".fxml").openStream());

            buscarControladorYSetGestor(entidad, loader, nombre, id); //Busca el controlador dependiendo de la entidad para setearle el gestor de base correspondiente
            Stage escenario = new Stage();
            escenario.setTitle(entidad);
            escenario.setScene(new Scene(root, width, height)); //TODO Cambiarlo(Cambiar los valores de width y height) en cada funcion dependiendo de la ventana.
            escenario.show();
            Stage actual = (Stage) ingresarEmpleado.getScene().getWindow();
            actual.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void buscarControladorYSetGestor(String entidad, FXMLLoader loader,String nombre, int id){

        switch(entidad){

            case "Supervisor":
                ControllerSupervisor controladorSupervisor = loader.getController();
                controladorSupervisor.gestorBDSupervisor = gestorBase;
                controladorSupervisor.supervisorLogueado = new Supervisor(nombre,id);
                controladorSupervisor.cargarDatosDefecto(); //erjknkerjngjerg
                break;
            case "Empleado":
                ControladorVentanaEmpleado controladorEmpleado = loader.getController();
                controladorEmpleado.gestorBDEmpleado = gestorBase;
                controladorEmpleado.empleadoLogueado = new Empleado(nombre,String.valueOf(id));
                break;
            case "Cliente":
                ControladorVentanaCliente controladorCliente = loader.getController();
                controladorCliente.gestorBDCliente = gestorBase;
                controladorCliente.clienteLogueado = new Cliente(nombre, id);
                controladorCliente.clienteLogueado.gestorBD = gestorBase;
        }

    }
}
