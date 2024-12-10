package com.aerochinquihue.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InicioController {

    @FXML
    private void reservarPasaje() {
        abrirVista("/com/aerochinquihue/view/ReservaView.fxml");
    }

    @FXML
    private void abrirVistaGerente() {
        abrirVista("/com/aerochinquihue/view/GerenteView.fxml");
    }

    private void abrirVista(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
