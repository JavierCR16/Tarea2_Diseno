package Modelo;

import java.util.ArrayList;
import java.util.Date;

public class Ticket {
    private Date fecha;
    private int clienteID;
    public GradoImportancia categoria;
    public EstadoTicket estado;
    public String asunto;
    public int id;
    private Cliente cliente;
    private Empleado empleado;
    public static ArrayList<Ticket> tickets = new ArrayList<>();

    public Ticket(String asunto, Cliente cliente){
        this.cliente = cliente;
        this.id = 0; //TODO Sacar el ID de la base de datos e insertar el nuevo ticket a la base
        this.categoria = GradoImportancia.SIN_CATALOGAR;
        this.estado = EstadoTicket.ATENDIDO.SIN_ATENDER;
        this.fecha = new Date();
        this.asunto = asunto;
        this.clienteID = cliente.ID;
        tickets.add(this);
    }

    public void setCategoria(GradoImportancia categoria) {
        this.categoria = categoria;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public int[] getDistribTicketsXFecha(Date fechaIni, Date fechaFin) {
        String query =
                "SELECT COUNT(*) FROM Tickets WHERE fecha BETWEEN " +
                        fechaIni.toString() + ", " + fechaFin.toString() + "GROUP BY categoria;";
        //TODO Hacer bien el query y convertir el resultado a tablas
        return new int[]{0, 0, 0};//TODO cambiar valores por resultado del query
    }

    public String getCategoriaMasRecibida() {
        String query = "SELECT categoria, COUNT(*) AS cantidad FROM Tickets WHERE categoria != \"SIN_CATALOGAR\" " +
                "GROUP BY categoria ORDER BY cantidad LIMIT 1;";
        //TODO Revisar query y cambiar resultado de funcion
        return "SIN_CATALOGAR";
    }


}
