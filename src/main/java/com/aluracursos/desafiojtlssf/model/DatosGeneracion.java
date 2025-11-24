package com.aluracursos.desafiojtlssf.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Record que representa los datos de una generación de Pokémon,
 * utilizados para construir la URL de la API.
 * * @param nombre El nombre de la generación (ej: "Kanto", "Johto").
 * @param offset El índice inicial en la Pokedex global para esta generación.
 * @param limit El número de Pokémon que abarca esta generación.
 */
public record DatosGeneracion(
        String nombre,
        int offset,
        int limit
) {
    // Mapa estático que almacena todas las generaciones de forma predefinida.
    private static final Map<Integer, DatosGeneracion> GENERACIONES = new LinkedHashMap<>();

    // Bloque estático para inicializar el mapa de generaciones.
    static {
        GENERACIONES.put(1, new DatosGeneracion("Kanto", 0, 151));
        GENERACIONES.put(2, new DatosGeneracion("Johto", 151, 100));
        GENERACIONES.put(3, new DatosGeneracion("Hoenn", 251, 135));
        GENERACIONES.put(4, new DatosGeneracion("Sinnoh", 386, 107));
        GENERACIONES.put(5, new DatosGeneracion("Teselia", 493, 156));
        GENERACIONES.put(6, new DatosGeneracion("Kalos", 649, 72));
        GENERACIONES.put(7, new DatosGeneracion("Alola", 721, 88));
        GENERACIONES.put(8, new DatosGeneracion("Galar", 809, 96));
        GENERACIONES.put(9, new DatosGeneracion("Paldea", 905, 120));
    }
}