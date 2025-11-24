package com.aluracursos.desafiojtlssf.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Record que representa una estadística base individual de un Pokémon.
 * * Ignora cualquier propiedad JSON que no esté mapeada.
 * * @param baseStat El valor numérico de la estadística base (ej: 45, 60). Mapeado desde el campo "base_stat" del JSON.
 * @param estadistica El objeto que contiene el nombre de la estadística. Mapeado desde el campo "stat" del JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEstadistica(
        @JsonAlias("base_stat") Integer baseStat,
        @JsonAlias("stat") DatosEstadisticasNombre estadistica
) {
}