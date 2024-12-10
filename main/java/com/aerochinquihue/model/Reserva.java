package com.aerochinquihue.model;

import java.time.LocalDate;

public class Reserva {
    private String tipo;
    private String destino;
    private double precio;
    private String cliente;
    private LocalDate fechaEntrega; // Asegúrate de tener este campo

    public Reserva(String tipo, String destino, double precio, String cliente) {
        this.tipo = tipo;
        this.destino = destino;
        this.precio = precio;
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "tipo='" + tipo + '\'' +
                ", destino='" + destino + '\'' +
                ", precio=" + precio +
                ", cliente='" + cliente + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                '}';
    }
}
