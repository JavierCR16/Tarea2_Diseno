package Controladores;

import Modelo.GradoImportancia;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEdit implements Initializable {
    public boolean caso;
    public String nombreO;
    public String idO;
    @FXML
    public Button guardar;
    @FXML
    public Label nombre;
    @FXML
    public Label id;
    @FXML
    public ComboBox<GradoImportancia> comboBox;
    @FXML
    public Label especializacion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!caso){
            guardar.setOnAction(event -> {
                GradoImportancia gradoImportancia = comboBox.getValue();
                Stage actual = (Stage) guardar.getScene().getWindow();
                actual.close();
                //TODO Query a la base para actualizar
            });
        }
    }

    public void iniciar(){
        if(caso){
            comboBox.setVisible(false);
            especializacion.setVisible(false);
            guardar.setText("Cerrar");
            guardar.setOnAction(event -> {
                Stage actual = (Stage) guardar.getScene().getWindow();
                actual.close();
            });
        }
        else {
            comboBox.getItems().addAll(GradoImportancia.values());
        }
        nombre.setText(nombreO);
        id.setText(idO);
    }
}
