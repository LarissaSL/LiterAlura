package br.com.alura.literAlura.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
