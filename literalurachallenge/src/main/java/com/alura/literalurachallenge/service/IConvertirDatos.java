package com.alura.literalurachallenge.service;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
