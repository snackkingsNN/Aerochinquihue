package com.aerochinquihue.model;

public class Pasaje {
    private String destino;
    private double precio;

    public Pasaje(String destino, double precio) {
        this.destino = destino;
        this.precio = precio;
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
}