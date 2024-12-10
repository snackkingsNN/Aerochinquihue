package com.aerochinquihue.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.control.Alert;

public class InicioController {
    @FXML
    private Button reservarPasajeButton;

    @FXML
    void reservarPasaje(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aerochinquihue/view/ReservaView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) reservarPasajeButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reserva de Pasajes");
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de Reserva de Pasajes.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
