package com.aerochinquihue.model;

public class Encomienda {
    private final String destino;
    private final double peso;
    private final double precio;

    public Encomienda(String destino, double peso, double precio) {
        this.destino = destino;
        this.peso = peso;
        this.precio = precio;
    }

    public String getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public double getPrecio() {
        return precio;
    }
}
