package com.aluracursos.desafiojtlssf.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}