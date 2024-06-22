package br.com.alura.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultsDTO(@JsonAlias("title") String titulo,
                         @JsonAlias("authors") List<AutorDTO> autores,
                         @JsonAlias("languages") List<String> idiomas,
                         @JsonAlias("download_count") Integer numeroDownloads,
                         @JsonAlias("formats") Map<String, String> formatos,
                         @JsonAlias("subjects") List<String> generos) {
}
