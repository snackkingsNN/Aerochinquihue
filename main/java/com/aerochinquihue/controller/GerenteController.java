package com.aerochinquihue.controller;

import com.aerochinquihue.model.Reserva;
import com.aerochinquihue.model.Registro;
import com.aerochinquihue.model.Vuelo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GerenteController {
    @FXML
    private TableView<Reserva> ventasTableView;
    @FXML
    private TableColumn<Reserva, String> clienteColumn;
    @FXML
    private TableColumn<Reserva, String> destinoColumn;
    @FXML
    private TableColumn<Reserva, String> tipoColumn;
    @FXML
    private TableColumn<Reserva, Double> precioColumn;

    @FXML
    private TextField destinoExtraField;
    @FXML
    private TextField precioPasajeExtraField;
    @FXML
    private TextField precioEncomiendaExtraField;
    @FXML
    private TextField clienteDescuentoField;
    @FXML
    private TextField descuentoField;

    private final Registro registro = new Registro();
    private final ObservableList<Reserva> reservasList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        destinoColumn.setCellValueFactory(new PropertyValueFactory<>("destino"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

        reservasList.addAll(registro.obtenerReservas());
        ventasTableView.setItems(reservasList);
    }

    @FXML
    private void anadirVueloExtraordinario() {
        String destino = destinoExtraField.getText();
        String precioPasajeText = precioPasajeExtraField.getText();
        String precioEncomiendaText = precioEncomiendaExtraField.getText();

        if (destino.isEmpty() || !isNumeric(precioPasajeText) || !isNumeric(precioEncomiendaText)) {
            mostrarAlerta("Error", "Completa todos los campos correctamente.");
            return;
        }

        double precioPasaje = Double.parseDouble(precioPasajeText);
        double precioEncomienda = Double.parseDouble(precioEncomiendaText);

        Vuelo vueloExtra = new Vuelo(destino, precioPasaje, precioEncomienda);
        registro.agregarVuelo(vueloExtra);
        mostrarAlerta("Vuelo Extraordinario", "Vuelo extraordinario añadido exitosamente.");
    }

    @FXML
    private void aplicarDescuentoEspecial() {
        String cliente = clienteDescuentoField.getText();
        String descuentoText = descuentoField.getText();

        if (cliente.isEmpty() || !isNumeric(descuentoText)) {
            mostrarAlerta("Error", "Completa todos los campos correctamente.");
            return;
        }

        double descuento = Double.parseDouble(descuentoText);

        if (descuento < 0 || descuento > 100) {
            mostrarAlerta("Error", "El descuento debe estar entre 0 y 100.");
            return;
        }

        // Aplicar el descuento especial a todas las reservas del cliente especificado
        for (Reserva reserva : registro.obtenerReservas()) {
            if (reserva.getCliente().equals(cliente)) {
                double nuevoPrecio = reserva.getPrecio() * (1 - descuento / 100);
                reserva.setPrecio(nuevoPrecio);
            }
        }

        mostrarAlerta("Descuento Especial", "Descuento especial aplicado exitosamente.");
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
