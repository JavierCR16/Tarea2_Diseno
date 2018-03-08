package Gestores;

import Modelo.*;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

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

                String sqlEmpleado = "INSERT INTO EMPLEADO (IDSUPERVISOR, NOMBRE) VALUES (?, ?)";
                try {
                    PreparedStatement insertarEmpleado = conexion.prepareStatement(sqlEmpleado, Statement.RETURN_GENERATED_KEYS);
                    insertarEmpleado.setInt(1, idSupervisor);
                    insertarEmpleado.setString(2, nombre);
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

    public int encontrarIDEspecializacion(String especializacion){

        int idEncontrado = 0;
        String sqlEspecializacion = "SELECT ID FROM GRADOIMPORTANCIA WHERE CATEGORIA = ?";

        try{

            PreparedStatement encontrarEspecializacion = conexion.prepareStatement(sqlEspecializacion);
            encontrarEspecializacion.setString(1,especializacion);
            ResultSet especializacionEncontrado = encontrarEspecializacion.executeQuery();

            while(especializacionEncontrado.next()){
                idEncontrado = Integer.valueOf(especializacionEncontrado.getString("ID"));
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
        return idEncontrado;

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

    public boolean agregarTicket(String asunto, int idCliente){
        String sqlCliente = "INSERT INTO TICKET (ASUNTO, IDCLIENTE, FECHA) VALUES (?, ?, ?)";
        try {
            PreparedStatement insertarTicket = conexion.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
            insertarTicket.setString(1, asunto);
            insertarTicket.setInt(2, idCliente);
            insertarTicket.setDate(3, new Date(new java.util.Date().getTime()));
            insertarTicket.executeUpdate();

            ResultSet buscarUltimoID = insertarTicket.getGeneratedKeys();
            int ultimoId = 0;
            if (buscarUltimoID.next()) {
                ultimoId = buscarUltimoID.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            invocarAlerta("Error en la base de datos");
            return false;
        }
        return true;
    }

    public ArrayList<Empleado> getEmpleadosSinEspecializar(){
        ArrayList<Empleado> empleadosNoEspecializados = new ArrayList<>();

        String obtenerEmpleados = "SELECT EMPLEADO.CODIGOTRABAJADOR as idEmp, EMPLEADO.NOMBRE as nomEmp FROM EMPLEADO,GRADOIMPORTANCIA " +
                "WHERE EMPLEADO.ESPECIALIZACION = GRADOIMPORTANCIA.ID AND CATEGORIA = 'Sin_Catalogar' ";

        try{
            PreparedStatement extraerEmpleados = conexion.prepareStatement(obtenerEmpleados);
            ResultSet empleadosObtenidos = extraerEmpleados.executeQuery();

            while(empleadosObtenidos.next()){
                String nombreEmpleado = empleadosObtenidos.getString("nomEmp");
                String idEmpleado = empleadosObtenidos.getString("idEmp");

                empleadosNoEspecializados.add(new Empleado(nombreEmpleado,idEmpleado));
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
        return empleadosNoEspecializados;
    }

    public ArrayList<Ticket> getTicketsSinCategorizar(){
        ArrayList<Ticket> ticketsNoCategorizados = new ArrayList<>();
        String obtenerTickets = "SELECT TICKET.ID as ticketId, TICKET.ASUNTO as ticketAsun,CLIENTE.NOMBRE AS nomCli,CLIENTE.ID as idCli FROM TICKET,CLIENTE,GRADOIMPORTANCIA " +
                "WHERE TICKET.IDCATEGORIA= GRADOIMPORTANCIA.ID AND TICKET.IDCLIENTE = CLIENTE.ID AND CATEGORIA = 'Sin_Catalogar' ";
        try{

            PreparedStatement extraerTickets = conexion.prepareStatement(obtenerTickets);
            ResultSet ticketsObtenidos = extraerTickets.executeQuery();

            while(ticketsObtenidos.next()){
                String asuntoTicket = ticketsObtenidos.getString("ticketAsun");
                Cliente cliente = new Cliente(ticketsObtenidos.getString("nomCli"),ticketsObtenidos.getString("idCli"));

                Ticket ticketSinCategorizar = new Ticket(asuntoTicket,cliente);
                ticketSinCategorizar.setId(ticketsObtenidos.getString("ticketId"));



                ticketsNoCategorizados.add(ticketSinCategorizar);
            }


        }catch(SQLException e){
            e.printStackTrace();
        }
        return ticketsNoCategorizados;
    }

    public ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> clientes= new ArrayList<>();
        String obtenerClientes = "SELECT ID,NOMBRE FROM CLIENTE";

        try{
            PreparedStatement extraerClientes = conexion.prepareStatement(obtenerClientes);
            ResultSet clientesObtenidos = extraerClientes.executeQuery();

            while(clientesObtenidos.next()){
                String nombreCliente = clientesObtenidos.getString("NOMBRE");
                String idCliente = clientesObtenidos.getString("ID");

              clientes.add(new Cliente(nombreCliente,idCliente));
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
        return clientes;
    }

    public void actualizarEmpleado(int id, String especializacion){
        int esp = encontrarIDEspecializacion(especializacion);
        String sqlEmpleado = "UPDATE EMPLEADO SET ESPECIALIZACION = ? WHERE CODIGOTRABAJADOR = ?;";
        try {
            PreparedStatement updateEmpleado = conexion.prepareStatement(sqlEmpleado, Statement.RETURN_GENERATED_KEYS);
            updateEmpleado.setInt(1, esp);
            updateEmpleado.setInt(2, id);
            updateEmpleado.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            invocarAlerta("Error al actualizar empleado");
        }
    }

    public void actualizarTicket(int id, String categoria){
        int cat = encontrarIDEspecializacion(categoria);
        String sqlTicket = "UPDATE TICKET SET IDCATEGORIA = ? WHERE ID = ?;";
        try {
            PreparedStatement updateTicket = conexion.prepareStatement(sqlTicket, Statement.RETURN_GENERATED_KEYS);
            updateTicket.setInt(1, cat);
            updateTicket.setInt(2, id);
            updateTicket.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            invocarAlerta("Error al actualizar Ticket");
        }
    }

    public String getTipoTicketsMasRecibidos(){
        String queryTicket = "SELECT TOP (1)categoria, COUNT(*) AS cantidad FROM GRADOIMPORTANCIA,TICKET WHERE IDCATEGORIA = GRADOIMPORTANCIA.ID " +
                "AND GRADOIMPORTANCIA.CATEGORIA != 'SIN_CATALOGAR' " +
                "GROUP BY categoria ORDER BY cantidad DESC";
        String categoriaEncontrada = "";
        try{
            PreparedStatement ticketMasRecibido = conexion.prepareStatement(queryTicket);
            ResultSet categoriaRecibida = ticketMasRecibido.executeQuery();

            while(categoriaRecibida.next()){
                categoriaEncontrada = categoriaRecibida.getString("categoria");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return categoriaEncontrada;
    }

    public TablaTickets getDistTicketsXFecha(String fechaIni, String fechaFin){
        String queryTicketFecha = "SELECT categoria, COUNT(*) as totalTickets FROM TICKET,GRADOIMPORTANCIA WHERE fecha BETWEEN ? AND ? AND GRADOIMPORTANCIA.ID = TICKET.IDCATEGORIA AND CATEGORIA != 'SIN_CATALOGAR' GROUP BY categoria";
        String[] cantidadTickets = {"0","0","0"};
        try{
            PreparedStatement extraerTicketsFecha = conexion.prepareStatement(queryTicketFecha);
            extraerTicketsFecha.setString(1,fechaIni);
            extraerTicketsFecha.setString(2,fechaFin);
            ResultSet cantidadesExtraidas = extraerTicketsFecha.executeQuery();
            while(cantidadesExtraidas.next()){
                switch(cantidadesExtraidas.getString("categoria")){
                    case "Amarillo":
                        cantidadTickets[0] =cantidadesExtraidas.getString("totalTickets");
                        break;
                    case "Rojo":
                        cantidadTickets[1] = cantidadesExtraidas.getString("totalTickets");
                        break;
                    case "Verde":
                        cantidadTickets[2] = cantidadesExtraidas.getString("totalTickets");
                        break;
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new TablaTickets(cantidadTickets[0],cantidadTickets[1],cantidadTickets[2]);
    }

    public void atenderTicket(int id, EstadoTicket estadoTicket){
        String sqlTicket = "UPDATE TICKET SET IDESTADO = ? WHERE ID = ?;";
        try {
            int e = encontrarIDEstado(estadoTicket);
            PreparedStatement updateTicket = conexion.prepareStatement(sqlTicket, Statement.RETURN_GENERATED_KEYS);
            updateTicket.setInt(1, e);
            updateTicket.setInt(2, id);
            updateTicket.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            invocarAlerta("Error al actualizar Ticket");
        }
    }

    public Ticket solicitarTicket(int idEmpleado){
        String sqlTicket = "SELECT T.ASUNTO, T.ID, E.CODIGOTRABAJADOR " +
                "FROM TICKET T INNER JOIN EMPLEADO E ON E.ESPECIALIZACION = T.IDCATEGORIA " +
                "WHERE E.CODIGOTRABAJADOR = ? AND T.IDESTADO = 1 AND T.IDEMPLEADO IS NULL;";
        try{
            PreparedStatement encontrarEspecializacion = conexion.prepareStatement(sqlTicket);
            encontrarEspecializacion.setInt(1, idEmpleado);
            ResultSet ticket = encontrarEspecializacion.executeQuery();
            if(!ticket.next())
                return null;
            String asunto = ticket.getString("ASUNTO");
            String id = ticket.getString("ID");
            asignarDatosTicket(idEmpleado, Integer.valueOf(id), EstadoTicket.ATENDIENDO);
            return new Ticket(asunto, id);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void asignarDatosTicket(int idEmpleado, int idTicket, EstadoTicket e){
        int estado = encontrarIDEstado(e);
        String sqlUpdate = "UPDATE TICKET SET IDEMPLEADO = ?, IDESTADO = ? WHERE ID = ?;";
        try {
            PreparedStatement update = conexion.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS);
            update.setInt(1, idEmpleado);
            update.setInt(2, estado);
            update.setInt(3, idTicket);
            update.executeUpdate();
        } catch (SQLException sqlError) {
            sqlError.printStackTrace();
            invocarAlerta("Error al actualizar ticket");
        }
    }

    public int encontrarIDEstado(EstadoTicket especializacion){
        int idEncontrado = 0;
        String sqlEspecializacion = "SELECT ID FROM ESTADOTICKET WHERE ESTADO = ?";
        try{
            PreparedStatement encontrarEspecializacion = conexion.prepareStatement(sqlEspecializacion);
            encontrarEspecializacion.setString(1,especializacion.toString());
            ResultSet especializacionEncontrado = encontrarEspecializacion.executeQuery();
            while(especializacionEncontrado.next()){
                idEncontrado = Integer.valueOf(especializacionEncontrado.getString("ID"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return idEncontrado;
    }

    public Ticket getTicketAtendiendo(int idEmpleado){
        String consultaString = "SELECT * FROM TICKET WHERE IDEMPLEADO = ? AND IDESTADO = 2;";
        try {
            PreparedStatement consulta = conexion.prepareStatement(consultaString, Statement.RETURN_GENERATED_KEYS);
            consulta.setInt(1, idEmpleado);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()){
                String asunto = rs.getString("ASUNTO");
                String id = rs.getString("ID");
                return new Ticket(asunto, id);
            }
        } catch (SQLException sqlError) {
            sqlError.printStackTrace();
            invocarAlerta("Error al obtener datos");
        }
        return null;
    }

    public ArrayList<TablaPorcentajeAtencion> getPorcentAtencionXEmpleado() {
        String queryPorcentaje = "SELECT CATEGORIA,EMPLEADO.NOMBRE, (count(*) *1.0)/(select count(*) FROM TICKET)*100 as porcentajeAtencion FROM TICKET,EMPLEADO,GRADOIMPORTANCIA,ESTADOTICKET " +
                " WHERE TICKET.IDCATEGORIA = GRADOIMPORTANCIA.ID AND TICKET.IDESTADO = ESTADOTICKET.ID AND TICKET.IDEMPLEADO = EMPLEADO.CODIGOTRABAJADOR " +
                " AND CATEGORIA != 'Sin_Catalogar' AND ESTADO != 'Sin_Atender' group by EMPLEADO.NOMBRE, CATEGORIA";
        ArrayList<TablaPorcentajeAtencion> porcentajes = new ArrayList<>();
        try{
            PreparedStatement obtenerPorcentajes = conexion.prepareStatement(queryPorcentaje);
            ResultSet porcentajesObtenidos = obtenerPorcentajes.executeQuery();


            while(porcentajesObtenidos.next()){
                String categoria = porcentajesObtenidos.getString("categoria");
                String nombreEmp = porcentajesObtenidos.getString("nombre");
                String porcentajeAdmision = porcentajesObtenidos.getString("porcentajeAtencion")+" %";

                porcentajes.add(new TablaPorcentajeAtencion(categoria,nombreEmp,porcentajeAdmision));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return porcentajes;
    }

    public ArrayList<Ticket> getTicketsXempleado(boolean caso){
        ArrayList<Ticket> atendidos= new ArrayList<>();
        int idEstado = 0;
        if (caso) //Caso para encontrar atendidos
            idEstado = encontrarIDEstado(EstadoTicket.ATENDIDO);
        else//caso para encontrar en atencion
            idEstado = encontrarIDEstado(EstadoTicket.ATENDIENDO);
        String obtenerClientes = "SELECT T.ID, IDEMPLEADO, G.CATEGORIA FROM TICKET T,GRADOIMPORTANCIA G, ESTADOTICKET  WHERE IDESTADO = ESTADOTICKET.ID AND G.ID= T.IDCATEGORIA AND IDESTADO = ?;";
        try{
            PreparedStatement extraerTickets = conexion.prepareStatement(obtenerClientes);
            extraerTickets.setInt(1, idEstado);
            ResultSet clientesObtenidos = extraerTickets.executeQuery();
            while(clientesObtenidos.next()){
                String idTicket = clientesObtenidos.getString("ID");
                String idEmpleado = clientesObtenidos.getString("IDEMPLEADO");
                String categoria = clientesObtenidos.getString("CATEGORIA");
                atendidos.add(new Ticket(idTicket, idEmpleado, categoria));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return atendidos;
    }
}
