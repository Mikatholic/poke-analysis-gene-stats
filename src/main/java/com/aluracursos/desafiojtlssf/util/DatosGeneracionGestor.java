package com.aluracursos.desafiojtlssf.util;

import com.aluracursos.desafiojtlssf.model.DatosGeneracion;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Clase de utilidad para gestionar y proporcionar acceso al mapa estático
 * de todas las generaciones de Pokémon, incluyendo sus offsets y límites.
 */
public class DatosGeneracionGestor {

    // Mapa estático y final para almacenar las generaciones. LinkedHashMap mantiene el orden de inserción.
    private static final Map<Integer, DatosGeneracion> GENERACIONES = new LinkedHashMap<>();

    // Bloque estático para la inicialización de todas las generaciones.
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

    /**
     * Obtiene el mapa completo de todas las generaciones.
     * @return Un mapa inmutable que contiene el número de generación como clave y los datos de la generación como valor.
     */
    public static Map<Integer, DatosGeneracion> getGeneraciones() {
        return GENERACIONES;
    }
}