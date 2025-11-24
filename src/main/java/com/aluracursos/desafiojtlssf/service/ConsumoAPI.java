package com.aluracursos.desafiojtlssf.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase de servicio encargada de realizar peticiones HTTP a la PokeAPI
 * y devolver la respuesta en formato JSON (String).
 */
public class ConsumoAPI {
    /**
     * Realiza una petición GET síncrona a la URL especificada.
     * @param url La dirección URL a consultar.
     * @return El cuerpo de la respuesta HTTP como una cadena JSON.
     * @throws RuntimeException Si ocurre un error de E/S o interrupción durante la petición.
     */
    public String obtenerDatos(String url) {
        // Crea un cliente HTTP.
        HttpClient client = HttpClient.newHttpClient();
        // Construye la petición HTTP con la URL.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            // Envía la petición y espera la respuesta como String.
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            // Manejo de errores de entrada/salida (ej: problema de conexión).
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // Manejo de interrupción del hilo (ej: tiempo de espera).
            throw new RuntimeException(e);
        }

        // Obtiene el cuerpo de la respuesta, que es el JSON.
        String json = response.body();
        return json;
    }
}