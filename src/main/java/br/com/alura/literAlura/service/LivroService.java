package br.com.alura.literAlura.service;

import br.com.alura.literAlura.dto.AutorDTO;
import br.com.alura.literAlura.dto.LivroDTO;
import br.com.alura.literAlura.dto.ResultsDTO;
import br.com.alura.literAlura.model.Livro;
import br.com.alura.literAlura.model.GeneroLivro;
import br.com.alura.literAlura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repositorio;

    @Autowired
    private ConverteDados conversor;

    @Autowired
    private ConsumoApi consumoApi;

    private static final String API_URL = "https://gutendex.com/books/?search=";

    public List<LivroDTO> popularBanco() {
        if (!tabelaEstaPopulada()) {
            List<LivroDTO> livrosInseridos = new ArrayList<>();

            String[] titulosParaBuscar = {
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
                    "Memorias Posthumas de Braz Cubas"
            };

            for (String titulo : titulosParaBuscar) {
                try {
                    String tituloFormatado = titulo.replace(" ", "+");
                    String url = API_URL + tituloFormatado;
                    String json = consumoApi.consumir(url);

                    LivroDTO livroDTO = conversor.obterDados(json, LivroDTO.class);

                    ResultsDTO resultado = livroDTO.resultados().get(0);

                    String tituloLivro = resultado.titulo();
                    String nomeAutor = resultado.autores().get(0).nome();
                    String idioma = resultado.idiomas().get(0).toUpperCase();
                    int numeroDownloads = resultado.numeroDownloads();
                    String poster = resultado.formatos().get("image/jpeg");
                    String genero = resultado.generos().isEmpty() ? "Outro" : mapearGenero(resultado.generos().get(0));

                    Livro livro = new Livro(tituloLivro, nomeAutor, idioma, numeroDownloads, poster, genero);
                    repositorio.save(livro);

                    livrosInseridos.add(new LivroDTO(List.of(resultado)));
                    System.out.println("Livro salvo no banco: " + livro.toString());
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

    private List<LivroDTO> converteDados(List<Livro> livros) {
        return livros.stream()
                .map(livro -> {
                    ResultsDTO resultsDTO = new ResultsDTO(
                            livro.getTitulo(),
                            List.of(new AutorDTO(livro.getAutor())),
                            List.of(livro.getIdioma()),
                            livro.getNumeroDownloads(),
                            obterMapaPoster(livro.getPoster()),
                            List.of(livro.getGenero())
                    );
                    return new LivroDTO(List.of(resultsDTO));
                })
                .collect(Collectors.toList());
    }

    private Map<String, String> obterMapaPoster(String poster) {
        Map<String, String> mapaPoster = new HashMap<>();
        mapaPoster.put("image/jpeg", poster);
        return mapaPoster;
    }

    public List<LivroDTO> listaTop5() {
        return converteDados(repositorio.findTop5ByOrderByNumeroDownloadsDesc());
    }

    public List<LivroDTO> listaPorGenero(String genero) {
        GeneroLivro generoAPesquisar = GeneroLivro.porNome(genero);
        if (generoAPesquisar == null) {
            return new ArrayList<>();
        }
        return converteDados(repositorio.findByGeneroIgnoreCase(generoAPesquisar.getNome()));
    }


    public List<LivroDTO> listaObrasDoAutor(String autor) {
        autor = autor.trim();
        return converteDados(repositorio.findByAutorContainingIgnoreCase(autor));
    }

    public List<LivroDTO> listaObra(String titulo) {
        return converteDados(repositorio.buscarPorTitulo(titulo));
    }


    public List<LivroDTO> listaObraDoIdioma(String idioma) {
        return converteDados(repositorio.buscarPorIdioma(idioma));
    }
}
