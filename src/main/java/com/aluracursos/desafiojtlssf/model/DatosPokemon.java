package com.aluracursos.desafiojtlssf.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Record que representa la información básica de un Pokémon en una lista.
 * Se utiliza para mapear los resultados de la llamada inicial a la API.
 * * Ignora cualquier propiedad JSON que no esté mapeada aquí.
 * * @param nombre El nombre del Pokémon. Mapeado desde el campo "name" del JSON.
 * @param url La URL específica para obtener los detalles de este Pokémon. Mapeado desde el campo "url" del JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosPokemon(
        @JsonAlias("name") String nombre,
        @JsonAlias("url") String url
) {
}