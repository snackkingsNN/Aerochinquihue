package com.aerochinquihue.model;

import java.util.ArrayList;
import java.util.List;

public class Registro {
    private final List<Reserva> reservas = new ArrayList<>();
    private final List<Vuelo> vuelos = new ArrayList<>();
    private final List<Encomienda> encomiendas = new ArrayList<>(); // Añadimos la lista de encomiendas

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public List<Reserva> obtenerReservas() {
        return new ArrayList<>(reservas);
    }

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
    }

    public List<Vuelo> obtenerVuelos() {
        return new ArrayList<>(vuelos);
    }

    public void agregarEncomienda(Encomienda encomienda) {
        encomiendas.add(encomienda);
    }

    public List<Encomienda> obtenerEncomiendas() {
        return new ArrayList<>(encomiendas);
    }
}
