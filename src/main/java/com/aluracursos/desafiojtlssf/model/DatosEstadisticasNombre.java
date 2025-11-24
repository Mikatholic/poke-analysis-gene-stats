package com.aluracursos.desafiojtlssf.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Record que representa el nombre de una estadística base.
 * Forma parte del objeto "stat" dentro de la estructura de estadísticas del Pokémon.
 * * Ignora cualquier propiedad JSON que no esté mapeada.
 * * @param nombre El nombre de la estadística (ej: "hp", "attack", "defense"). Mapeado desde el campo "name" del JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEstadisticasNombre(
        @JsonAlias("name") String nombre
) {
}