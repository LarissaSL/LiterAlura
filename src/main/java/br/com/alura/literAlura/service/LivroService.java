package br.com.alura.literAlura.service;

import br.com.alura.literAlura.dto.LivroDTO;
import br.com.alura.literAlura.model.Livro;
import br.com.alura.literAlura.model.GeneroLivro;
import br.com.alura.literAlura.repository.LivroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repositorio;

    @Autowired
    private ConsumoApi consumoApi;

    private static final String API_URL = "https://gutendex.com/books/?search=";

    private List<LivroDTO> converteDados(List<Livro> livros) {
        return livros.stream()
                .map(l -> new LivroDTO(l.getTitulo(), l.getAutor(), l.getIdioma(), l.getNumeroDownloads(), l.getPoster(), l.getGenero()))
                .collect(Collectors.toList());
    }

    public List<LivroDTO> popularBanco() {
        if (!tabelaEstaPopulada()) {
            List<LivroDTO> livrosInseridos = new ArrayList<>();

            String[] livrosParaBuscar = {
                    "Pride and Prejudice",
                    "Romeo and Juliet",
                    "Moby Dick",
                    "Frankenstein",
                    "Alice's Adventures in Wonderland",
                    "Middlemarch",
                    "A Room with a View",
                    "The Blue Castle: a novel",
                    "Cranford",
                    "Dom Casmurro",
                    "Le comte de Monte-Cristo, Tome I",
                    "Don Quijote",
                    "La Odisea",
                    "Memorias Posthumas de Braz Cubas",
            };

            for (String titulo : livrosParaBuscar) {
                try {
                    String tituloFormatado = titulo.replace(" ", "+");
                    String url = API_URL + tituloFormatado;
                    String json = consumoApi.obterDados(url);

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(json);

                    JsonNode resultsNode = root.get("results");
                    if (resultsNode != null && resultsNode.isArray() && resultsNode.size() > 0) {
                        JsonNode primeiroLivroNode = resultsNode.get(0);

                        String tituloLivro = primeiroLivroNode.get("title").asText();
                        String nomeAutor = primeiroLivroNode.get("authors").get(0).get("name").asText();
                        String idioma = primeiroLivroNode.get("languages").get(0).asText().toUpperCase();
                        int numeroDownloads = primeiroLivroNode.get("download_count").asInt();

                        String poster = null;
                        JsonNode formatos = primeiroLivroNode.get("formats");
                        if (formatos != null && formatos.has("image/jpeg")) {
                            poster = formatos.get("image/jpeg").asText();
                        }

                        String genero = "Indefinido";
                        JsonNode generos = primeiroLivroNode.get("subjects");
                        if (generos != null && generos.isArray() && generos.size() > 0) {
                            JsonNode primeiroGenero = generos.get(0);
                            if (primeiroGenero != null) {
                                genero = primeiroGenero.asText();
                                genero = mapearGenero(genero);
                            }
                        }

                        Livro livro = new Livro(tituloLivro, nomeAutor, idioma, numeroDownloads, poster, genero);
                        repositorio.save(livro);
                        livrosInseridos.add(new LivroDTO(tituloLivro, nomeAutor, idioma, numeroDownloads, poster, genero));
                        System.out.println("Livro salvo no banco: " + livro.toString());
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao inserir o Título: " + titulo);
                    e.printStackTrace();
                }
            }
            return livrosInseridos;
        } else {
            return listaDadosDisponiveisNoBanco();
        }
    }

    private boolean tabelaEstaPopulada() {
        return !repositorio.findAll().isEmpty();
    }

    private String mapearGenero(String genero) {
        if (genero.toLowerCase().contains("fiction")) {
            return GeneroLivro.FICCAO.getNome();
        } else if (genero.toLowerCase().contains("adventure")) {
            return GeneroLivro.AVENTURA.getNome();
        } else if (genero.toLowerCase().contains("drama")) {
            return GeneroLivro.DRAMA.getNome();
        } else {
            return "Outro";
        }
    }

    public List<LivroDTO> listaDadosDisponiveisNoBanco() {
        return converteDados(repositorio.findAll());
    }

}
