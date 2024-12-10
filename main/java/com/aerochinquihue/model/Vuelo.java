package com.aerochinquihue.model;

public class Vuelo {
    private final String destino;
    private final double precioPasaje;
    private final double precioEncomienda;

    public Vuelo(String destino, double precioPasaje, double precioEncomienda) {
        this.destino = destino;
        this.precioPasaje = precioPasaje;
        this.precioEncomienda = precioEncomienda;
    }

    public String getDestino() {
        return destino;
    }

    public double getPrecioPasaje() {
        return precioPasaje;
    }

    public double getPrecioEncomienda() {
        return precioEncomienda;
    }
}
