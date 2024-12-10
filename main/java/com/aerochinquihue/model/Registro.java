package com.aerochinquihue.model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Registro {
    private final List<Reserva> reservas = new ArrayList<>();
    private final List<Vuelo> vuelos = new ArrayList<>();
    private final List<Encomienda> encomiendas = new ArrayList<>();

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
        guardarEnArchivo("reservas.txt", reserva.toString());
    }

    public List<Reserva> obtenerReservas() {
        return new ArrayList<>(reservas);
    }

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
        guardarEnArchivo("vuelos.txt", vuelo.toString());
    }

    public List<Vuelo> obtenerVuelos() {
        return new ArrayList<>(vuelos);
    }

    public void agregarEncomienda(Encomienda encomienda) {
        encomiendas.add(encomienda);
        guardarEnArchivo("encomiendas.txt", encomienda.toString());
    }

    public List<Encomienda> obtenerEncomiendas() {
        return new ArrayList<>(encomiendas);
    }

    private void guardarEnArchivo(String archivo, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
