package com.aerochinquihue.controller;

import com.aerochinquihue.model.Reserva;
import com.aerochinquihue.model.Vuelo;
import com.aerochinquihue.model.DataLoader;
import com.aerochinquihue.model.Registro;
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

public class ReservaController {
    // Eliminamos el método y la variable no utilizados
    // private static boolean estadoEmergencia = false;

    @FXML
    private TextField clienteField;
    @FXML
    private ComboBox<String> vueloComboBox;
    @FXML
    private ComboBox<String> tipoReservaComboBox;
    @FXML
    private ComboBox<String> tipoAvionComboBox;
    @FXML
    private TextField pesoField;
    @FXML
    private TextField asientosField;
    @FXML
    private TextField precioField;

    @FXML
    private TableView<Vuelo> vuelosTableView;
    @FXML
    private TableColumn<Vuelo, String> destinoColumn;
    @FXML
    private TableColumn<Vuelo, Double> precioPasajeColumn;
    @FXML
    private TableColumn<Vuelo, Double> precioEncomiendaColumn;

    private final ObservableList<String> tipoReservaList = FXCollections.observableArrayList("Pasaje", "Encomienda");
    private final ObservableList<String> tipoAvionList = FXCollections.observableArrayList("CESSNA 310 (5 pasajeros)", "CESSNA 208 CARAVAN (9 pasajeros)", "LET 410 UVP-E20 (19 pasajeros)");
    private List<Vuelo> vuelosDisponibles;
    private final ObservableList<Vuelo> vueloList = FXCollections.observableArrayList();
    private final Registro registro = new Registro();

    @FXML
    public void initialize() {
        vuelosDisponibles = DataLoader.cargarVuelos();
        vueloComboBox.setItems(FXCollections.observableArrayList(DataLoader.getDestinos(vuelosDisponibles)));
        tipoReservaComboBox.setItems(tipoReservaList);
        tipoAvionComboBox.setItems(tipoAvionList);

        destinoColumn.setCellValueFactory(new PropertyValueFactory<>("destino"));
        precioPasajeColumn.setCellValueFactory(new PropertyValueFactory<>("precioPasaje"));
        precioEncomiendaColumn.setCellValueFactory(new PropertyValueFactory<>("precioEncomienda"));

        vueloList.addAll(vuelosDisponibles);
        vuelosTableView.setItems(vueloList);

        tipoReservaComboBox.valueProperty().addListener((obs, oldVal, newVal) -> actualizarCampos());
        vueloComboBox.valueProperty().addListener((obs, oldVal, newVal) -> actualizarCampos());
        tipoAvionComboBox.valueProperty().addListener((obs, oldVal, newVal) -> actualizarCampos());
        asientosField.textProperty().addListener((obs, oldVal, newVal) -> actualizarPrecio());
        pesoField.textProperty().addListener((obs, oldVal, newVal) -> actualizarPrecioEncomienda());
    }

    private void actualizarCampos() {
        String tipoReserva = tipoReservaComboBox.getValue();
        String destino = vueloComboBox.getValue();
        String tipoAvion = tipoAvionComboBox.getValue();

        if (tipoReserva != null && destino != null) {
            Vuelo vuelo = encontrarVueloPorDestino(destino);
            if (vuelo != null) {
                if (tipoReserva.equals("Pasaje")) {
                    precioField.setText(String.valueOf(vuelo.getPrecioPasaje()));
                    pesoField.setDisable(true);
                    asientosField.setDisable(false);
                } else if (tipoReserva.equals("Encomienda")) {
                    pesoField.setDisable(false);
                    asientosField.setDisable(true);
                    actualizarPrecioEncomienda();
                }
            }
        }
    }

    private void actualizarPrecio() {
        String tipoReserva = tipoReservaComboBox.getValue();
        String destino = vueloComboBox.getValue();
        String asientosText = asientosField.getText();

        if (tipoReserva != null && tipoReserva.equals("Pasaje") && isNumeric(asientosText)) {
            Vuelo vuelo = encontrarVueloPorDestino(destino);
            if (vuelo != null) {
                double asientos = Double.parseDouble(asientosText);
                double precioPasaje = vuelo.getPrecioPasaje();
                double precio = asientos * precioPasaje;
                precioField.setText(String.valueOf(precio));
            }
        }
    }

    private void actualizarPrecioEncomienda() {
        String tipoReserva = tipoReservaComboBox.getValue();
        String destino = vueloComboBox.getValue();
        String pesoText = pesoField.getText();
        String tipoAvion = tipoAvionComboBox.getValue();

        if (tipoReserva != null && tipoReserva.equals("Encomienda") && isNumeric(pesoText)) {
            Vuelo vuelo = encontrarVueloPorDestino(destino);
            if (vuelo != null && tipoAvion != null) {
                double peso = Double.parseDouble(pesoText);
                double precioEncomienda = vuelo.getPrecioEncomienda();
                double factorTipoAvion = obtenerFactorTipoAvion(tipoAvion);
                double precio = peso * precioEncomienda * factorTipoAvion;
                precioField.setText(String.valueOf(precio));
                precioField.setEditable(false);
            }
        }
    }

    private double obtenerFactorTipoAvion(String tipoAvion) {
        switch (tipoAvion) {
            case "CESSNA 310 (5 pasajeros)":
                return 1.0;
            case "CESSNA 208 CARAVAN (9 pasajeros)":
                return 1.5;
            case "LET 410 UVP-E20 (19 pasajeros)":
                return 2.0;
            default:
                return 1.0;
        }
    }

    public void reservar() {
        String cliente = clienteField.getText();
        String destino = vueloComboBox.getValue();
        String tipoReserva = tipoReservaComboBox.getValue();
        String tipoAvion = tipoAvionComboBox.getValue();
        String pesoText = pesoField.getText();
        String asientosText = asientosField.getText();
        String precioText = precioField.getText();

        if (tipoReserva != null && destino != null && tipoAvion != null && isNumeric(precioText)) {
            double precio = Double.parseDouble(precioText);
            Vuelo vuelo = encontrarVueloPorDestino(destino);

            if (vuelo != null) {
                Reserva reserva;
                if (tipoReserva.equals("Pasaje")) {
                    double asientos = Double.parseDouble(asientosText);
                    precio = asientos * vuelo.getPrecioPasaje();
                    if (clienteHaRealizadoMasDe10Vuelos(cliente)) {
                        precio *= 0.9; // Aplicar descuento del 10%
                    }
                    reserva = new Reserva("Pasaje", destino, precio, cliente);
                } else if (tipoReserva.equals("Encomienda")) {
                    double peso = Double.parseDouble(pesoText);
                    precio = peso * vuelo.getPrecioEncomienda() * obtenerFactorTipoAvion(tipoAvion);
                    reserva = new Reserva("Encomienda", destino, precio, cliente);
                } else {
                    mostrarAlerta("Error", "Tipo de reserva no válido.");
                    return;
                }

                registro.agregarReserva(reserva);
                mostrarAlerta("Éxito", "Reserva realizada: Cliente=" + cliente + ", Vuelo=" + destino + ", Tipo=" + tipoReserva + ", Tipo de Avión=" + tipoAvion + ", Precio=" + precio);
            } else {
                mostrarAlerta("Error", "Destino no encontrado.");
            }
        } else {
            mostrarAlerta("Error", "Completa todos los campos y asegúrate de que el peso/precio sea un número válido.");
        }
    }

    private boolean clienteHaRealizadoMasDe10Vuelos(String cliente) {
        // implementar para verificar si el cliente ha realizado mas de 10 vuelos
        return false;
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
