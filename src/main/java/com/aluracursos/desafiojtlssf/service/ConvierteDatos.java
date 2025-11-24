package com.aluracursos.desafiojtlssf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase de servicio para la conversión de datos JSON a objetos Java,
 * implementando la interfaz IConvierteDatos (asumida).
 */
public class ConvierteDatos implements IConvierteDatos{
    // Objeto principal de Jackson para realizar la serialización/deserialización.
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convierte una cadena JSON a un objeto de la clase especificada.
     * @param json La cadena JSON a convertir.
     * @param clase La clase de destino para el objeto Java.
     * @return Una instancia del objeto Java mapeado.
     * @throws RuntimeException Si el procesamiento del JSON falla (ej: formato incorrecto).
     */
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            // Intenta leer el JSON y mapearlo a la clase de destino.
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            // Lanza una excepción en caso de error de procesamiento JSON.
            throw new RuntimeException(e);
        }
    }
}