package com.aerochinquihue.controller;

import com.aerochinquihue.model.Encomienda;
import com.aerochinquihue.model.Registro;
import com.aerochinquihue.model.Vuelo;
import com.aerochinquihue.model.DataLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class EncomiendaController {
    @FXML
    private TextField clienteField;
    @FXML
    private ComboBox<String> vueloComboBox;
    @FXML
    private TextField pesoField;
    @FXML
    private TextField precioField;

    private final Registro registro = new Registro();
    private List<Vuelo> vuelosDisponibles;

    @FXML
    public void initialize() {
        vuelosDisponibles = DataLoader.cargarVuelos();
        vueloComboBox.setItems(FXCollections.observableArrayList(DataLoader.getDestinos(vuelosDisponibles)));

        pesoField.textProperty().addListener((obs, oldVal, newVal) -> actualizarPrecioEncomienda());
    }

    private void actualizarPrecioEncomienda() {
        String destino = vueloComboBox.getValue();
        String pesoText = pesoField.getText();

        if (destino != null && isNumeric(pesoText)) {
            Vuelo vuelo = encontrarVueloPorDestino(destino);
            if (vuelo != null) {
                double peso = Double.parseDouble(pesoText);
                double precio = peso * vuelo.getPrecioEncomienda();
                precioField.setText(String.valueOf(precio));
                precioField.setEditable(false);
            }
        }
    }

    public void reservarEncomienda() {
        String cliente = clienteField.getText();
        String destino = vueloComboBox.getValue();
        String pesoText = pesoField.getText();
        String precioText = precioField.getText();

        if (destino != null && isNumeric(pesoText) && isNumeric(precioText)) {
            double peso = Double.parseDouble(pesoText);
            double precio = Double.parseDouble(precioText);
            Encomienda encomienda = new Encomienda(destino, peso, precio);

            registro.agregarEncomienda(encomienda);
            mostrarAlerta("Éxito", "Encomienda reservada: Cliente=" + cliente + ", Destino=" + destino + ", Peso=" + peso + "kg, Precio=" + precio);
        } else {
            mostrarAlerta("Error", "Completa todos los campos y asegúrate de que el peso/precio sean números válidos.");
        }
    }

    private Vuelo encontrarVueloPorDestino(String destino) {
        return vuelosDisponibles.stream()
                .filter(v -> v.getDestino().equalsIgnoreCase(destino))
                .findFirst()
                .orElse(null);
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
