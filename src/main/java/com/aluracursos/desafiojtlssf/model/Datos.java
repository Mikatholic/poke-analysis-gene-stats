package com.aluracursos.desafiojtlssf.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Record que representa la respuesta JSON de una lista de recursos de la API (ej: lista de Pokémon de una generación).
 * * Ignora cualquier propiedad JSON que no esté mapeada.
 * * @param conteo El número total de recursos disponibles. Mapeado desde el campo "count".
 * @param siguiente La URL para obtener la siguiente página de resultados (si aplica). Mapeado desde el campo "next".
 * @param anterior La URL para obtener la página anterior de resultados (si aplica). Mapeado desde el campo "previous".
 * @param nombresPokemon La lista de resultados, que contiene los nombres y URLs de los Pokémon. Mapeado desde el campo "results".
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("count") Integer conteo,
        @JsonAlias("next") String siguiente,
        @JsonAlias("previous") String anterior,
        @JsonAlias("results") List<DatosPokemon> nombresPokemon
) {
}