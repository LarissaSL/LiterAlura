package br.com.alura.literAlura.dto;

public record LivroDTO(String titulo,
                       String autor,
                       String idioma,
                       Integer numeroDownloads,
                       String poster,
                       String genero) {
}
