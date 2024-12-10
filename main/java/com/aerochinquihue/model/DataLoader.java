package com.aerochinquihue.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataLoader {
    public static List<Vuelo> cargarVuelos() {
        List<Vuelo> vuelos = new ArrayList<>();
        vuelos.add(new Vuelo("Cochamó", 20000, 5000));
        vuelos.add(new Vuelo("Puelo Bajo", 20000, 5000));
        vuelos.add(new Vuelo("Contao", 20000, 5000));
        vuelos.add(new Vuelo("Rio Negro", 25000, 6000));
        vuelos.add(new Vuelo("Pupelde", 25000, 6000));
        vuelos.add(new Vuelo("Chepu", 30000, 8000));
        vuelos.add(new Vuelo("Ayacara", 30000, 8000));
        vuelos.add(new Vuelo("Pillán", 40000, 12000));
        vuelos.add(new Vuelo("Reñihue", 40000, 12000));
        vuelos.add(new Vuelo("Isla Quenac", 40000, 12000));
        vuelos.add(new Vuelo("Palqui", 40000, 12000));
        vuelos.add(new Vuelo("Chaitén", 50000, 15000));
        vuelos.add(new Vuelo("Santa Bárbara", 50000, 15000));
        return vuelos;
    }

    public static List<String> getDestinos(List<Vuelo> vuelos) {
        return vuelos.stream().map(Vuelo::getDestino).collect(Collectors.toList());
    }
}
