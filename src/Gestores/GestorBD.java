package Gestores;

import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.GradoImportancia;
import Modelo.Supervisor;
import javafx.scene.control.Alert;

import java.sql.*;

/**
 * Created by Javier on 2/19/2018.
 */
public class GestorBD {

    private Connection conexion;
    private Statement estado;

    public GestorBD() {
        conexion = null;
        estado = null;
    }

    public void establecerConexionSuperUsuario() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=BaseTarea2Diseno;integratedSecurity=true;";
            conexion = DriverManager.getConnection(connectionUrl);
            estado = conexion.createStatement();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;

    }

    public Statement getEstado() {
        return estado;

    }

    public void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
                conexion = null;
                estado = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void establecerConexionUsuario(String username, String password) {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=BaseTarea2Diseno;user=" + username
                    + ";password=" + password;
            conexion = DriverManager.getConnection(connectionUrl);
            estado = conexion.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean existeConexionUsuarios(String username, String password) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=BaseTarea2Diseno;user=" + username
                    + ";password=" + password;
            connection = DriverManager.getConnection(connectionUrl);
            connection.createStatement();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public void invocarAlerta(String mensaje) {

        Alert nuevaAlerta = new Alert(Alert.AlertType.WARNING);
        nuevaAlerta.setTitle("Error");
        nuevaAlerta.setContentText(mensaje);
        nuevaAlerta.showAndWait();

    }

    public boolean existeEntidad(String nombre, int id, String tablaBuscar) {
        establecerConexionSuperUsuario(); // Para cuando se valida un supervisor
        String sqlEntidad = "";
        switch (tablaBuscar) {

            case "EMPLEADO":
                sqlEntidad = "SELECT * FROM " + tablaBuscar + " WHERE CODIGOTRABAJADOR = ? AND NOMBRE = ?";
                break;
            default:
                sqlEntidad = "SELECT * FROM " + tablaBuscar + " WHERE ID = ? AND NOMBRE = ?";
                break;
        }

        try {

            PreparedStatement obtenerEntidad = conexion.prepareStatement(sqlEntidad);
            obtenerEntidad.setInt(1, id);
            obtenerEntidad.setString(2, nombre);
            ResultSet resultados = obtenerEntidad.executeQuery();

            if (resultados.next()) {
                cerrarConexion();
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion();
        return false;
    }

    public void agregarClienteEmpleado(String nombre, String opcion, int idSupervisor) { //Solo se envia el id supervisor para que este sea agregado al empleado

        switch (opcion) {

            case "Cliente":

                String sqlCliente = "INSERT INTO CLIENTE (NOMBRE) VALUES (?)";
                try {
                    PreparedStatement insertarCliente = conexion.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
                    insertarCliente.setString(1, nombre);
                    insertarCliente.executeUpdate();

                    ResultSet buscarUltimoID = insertarCliente.getGeneratedKeys();
                    int ultimoId = 0;
                    if (buscarUltimoID.next()) {
                        ultimoId = buscarUltimoID.getInt(1);
                    }
                    //Aqui se invoca el stored procedure para generar un login al cliente.
                    generarLogIN(nombre, String.valueOf(ultimoId));
                } catch (SQLException e) {
                    e.printStackTrace();
                    invocarAlerta(nombre + " ya existe en la base de datos");
                }
                break;

            case "Empleado":

                String sqlEmpleado = "INSERT INTO EMPLEADO (IDSUPERVISOR, NOMBRE,ESPECIALIZACION) VALUES (?, ?, ?)";
                try {
                    PreparedStatement insertarEmpleado = conexion.prepareStatement(sqlEmpleado, Statement.RETURN_GENERATED_KEYS);
                    insertarEmpleado.setInt(1, idSupervisor); //TODO PONER ID SUPERVISOR
                    insertarEmpleado.setString(2, nombre);
                    insertarEmpleado.setString(3, GradoImportancia.SIN_CATALOGAR.toString());
                    insertarEmpleado.executeUpdate();

                    ResultSet buscarUltimoID = insertarEmpleado.getGeneratedKeys();
                    int ultimoId = 0;
                    if (buscarUltimoID.next()) {
                        ultimoId = buscarUltimoID.getInt(1);
                    }
                    //Aqui se invoca el stored procedure para generar un login al cliente.
                    generarLogIN(nombre, String.valueOf(ultimoId));
                } catch (SQLException e) {
                    e.printStackTrace();
                    invocarAlerta(nombre + " ya existe en la base de datos");
                }
                break;
        }
    }

    public void generarLogIN(String nombre, String contrasena) {
        try {
            CallableStatement procedureLogIn = conexion.prepareCall("{call crearLogin(?, ?)}");
            procedureLogIn.setString(1, nombre);
            procedureLogIn.setString(2, contrasena);
            procedureLogIn.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarTicket(String asunto, int idCliente){
        String sqlCliente = "INSERT INTO TICKET (ASUNTO) VALUES (?)";
        try {
            PreparedStatement insertarTicket = conexion.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
            insertarTicket.setString(1, asunto);
            insertarTicket.executeUpdate();

            ResultSet buscarUltimoID = insertarTicket.getGeneratedKeys();
            int ultimoId = 0;
            if (buscarUltimoID.next()) {
                ultimoId = buscarUltimoID.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            invocarAlerta("Error en la base de datos");
        }
    }
}
