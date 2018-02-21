package Controladores;

import Gestores.GestorBD;
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
import java.sql.SQLException;
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

            loguearEntidad(nombreSuper,idSuper,"Supervisor",1200,600);

        });

        ingresarEmpleado.setOnAction(event -> {
            String nombreEmp = nombreEmpleado.getText();
            int idEmp = Integer.valueOf(idEmpleado.getText());

            loguearEntidad(nombreEmp,idEmp,"Empleado",1200,600);

        });



        ingresarCliente.setOnAction(event -> {
            String nombreCli = nombreCliente.getText();
            int idCli = Integer.valueOf(idCliente.getText());

            loguearEntidad(nombreCli,idCli,"Cliente",1200,600);

        });


    }

    public void loguearEntidad(String nombre, int id , String nombreTabla, int width, int height) {


        if (gestorBase.existeConexionUsuarios(nombre, String.valueOf(id)) && gestorBase.existeEntidad(nombre, id,nombreTabla.toUpperCase())) {

            gestorBase.establecerConexionUsuario(nombre,String.valueOf(id));
            abrirVentanaEntidad(nombreTabla,width,height);
        }
        else{
            gestorBase.invocarAlerta("No existe un usuario asociado a: "+nombre);
        }


    }

    public void abrirVentanaEntidad(String entidad , int width, int height) {

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../Interfaz/"+entidad+".fxml").openStream());

            buscarControladorYSetGestor(entidad, loader); //Busca el controlador dependiendo de la entidad para setearle el gestor de base correspondiente
            Stage escenario = new Stage();
            escenario.setTitle(entidad);
            escenario.setScene(new Scene(root, width, height)); //TODO Cambiarlo(Cambiar los valores de width y height) en cada funcion dependiendo de la ventana.
            escenario.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void buscarControladorYSetGestor(String entidad, FXMLLoader loader){

        switch(entidad){

            case "Supervisor":
                ControllerSupervisor controladorSupervisor = loader.getController();
                controladorSupervisor.gestorBDSupervisor = gestorBase;
                break;
            case "Empleado":
                ControladorVentanaEmpleado controladorEmpleado = loader.getController();
                controladorEmpleado.gestorBDEmpleado = gestorBase;
                break;
            case "Cliente":
                ControladorVentanaCliente controladorCliente = loader.getController();
                controladorCliente.gestorBDCliente = gestorBase;
        }

    }
}
