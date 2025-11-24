package com.aluracursos.desafiojtlssf.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Record que representa el detalle completo de un Pokémon.
 * Mapea los campos esenciales como nombre, medidas y lista de estadísticas.
 * * Ignora cualquier propiedad JSON que no esté mapeada.
 * * @param nombre El nombre del Pokémon. Mapeado desde el campo "name" del JSON.
 * @param altura La altura del Pokémon, generalmente en decímetros (dm). Mapeado desde el campo "height" del JSON.
 * @param peso El peso del Pokémon, generalmente en hectogramos (hg). Mapeado desde el campo "weight" del JSON.
 * @param estadisticas La lista de todas las estadísticas base del Pokémon. Mapeado desde el campo "stats" del JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosPokemonDetalle(
        @JsonAlias("name") String nombre,
        @JsonAlias("height") Integer altura,
        @JsonAlias("weight") Integer peso,
        @JsonAlias("stats") List<DatosEstadistica> estadisticas
) {
    /**
     * Convierte la altura de decímetros (unidad API) a metros.
     * @return La altura en metros (double).
     */
    public double getAlturaMetros() {
        // La altura en la API está en decímetros (1m = 10 dm).
        return (double) altura / 10.0;
    }

    /**
     * Convierte el peso de hectogramos (unidad API) a kilogramos.
     * @return El peso en kilogramos (double).
     */
    public double getPesoKilogramos() {
        // El peso en la API está en hectogramos (1kg = 10 hg).
        return (double) peso / 10.0;
    }
}