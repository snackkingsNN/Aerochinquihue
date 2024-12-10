package com.aerochinquihue.controller;

import com.aerochinquihue.model.DataLoader;
import com.aerochinquihue.model.Reserva;
import com.aerochinquihue.model.Registro;
import com.aerochinquihue.model.Vuelo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

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
    private TableView<Vuelo> vuelosTableView;
    @FXML
    private TableColumn<Vuelo, String> destinoVueloColumn;
    @FXML
    private TableColumn<Vuelo, Double> precioPasajeVueloColumn;
    @FXML
    private TableColumn<Vuelo, Double> precioEncomiendaVueloColumn;

    @FXML
    private ComboBox<String> destinoExtraComboBox;
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
    private final ObservableList<Vuelo> vuelosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        destinoColumn.setCellValueFactory(new PropertyValueFactory<>("destino"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

        destinoVueloColumn.setCellValueFactory(new PropertyValueFactory<>("destino"));
        precioPasajeVueloColumn.setCellValueFactory(new PropertyValueFactory<>("precioPasaje"));
        precioEncomiendaVueloColumn.setCellValueFactory(new PropertyValueFactory<>("precioEncomienda"));

        reservasList.addAll(registro.obtenerReservas());
        ventasTableView.setItems(reservasList);

        vuelosList.addAll(registro.obtenerVuelos());
        vuelosTableView.setItems(vuelosList);

        List<Vuelo> vuelosDisponibles = DataLoader.cargarVuelos();
        destinoExtraComboBox.setItems(FXCollections.observableArrayList(DataLoader.getDestinos(vuelosDisponibles)));
    }

    @FXML
    private void anadirVueloExtraordinario() {
        String destino = destinoExtraComboBox.getValue();
        String precioPasajeText = precioPasajeExtraField.getText();
        String precioEncomiendaText = precioEncomiendaExtraField.getText();

        if (destino == null || !isNumeric(precioPasajeText) || !isNumeric(precioEncomiendaText)) {
            mostrarAlerta("Error", "Completa todos los campos correctamente.");
            return;
        }

        double precioPasaje = Double.parseDouble(precioPasajeText);
        double precioEncomienda = Double.parseDouble(precioEncomiendaText);

        Vuelo vueloExtra = new Vuelo(destino, precioPasaje, precioEncomienda);
        registro.agregarVuelo(vueloExtra);
        vuelosList.add(vueloExtra);
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

        for (Reserva reserva : registro.obtenerReservas()) {
            if (reserva.getCliente().equals(cliente)) {
                double nuevoPrecio = reserva.getPrecio() * (1 - descuento / 100);
                reserva.setPrecio(nuevoPrecio);
            }
        }

        reservasList.clear();
        reservasList.addAll(registro.obtenerReservas());
        ventasTableView.refresh();
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
