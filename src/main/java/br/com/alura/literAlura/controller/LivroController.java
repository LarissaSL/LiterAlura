package br.com.alura.literAlura.controller;

import br.com.alura.literAlura.dto.LivroDTO;
import br.com.alura.literAlura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService servico;

    @GetMapping("")
    public List<LivroDTO> mostrarTodos() {
        return servico.listaDadosDisponiveisNoBanco();
    }

    @GetMapping("/top5")
    public List<LivroDTO> obterTop5() {
        return servico.listaTop5();
    }

    @GetMapping("/genero/{genero}")
    public List<LivroDTO> obterPorGenero(@PathVariable String genero) {
        return servico.listaPorGenero(genero);
    }

    @GetMapping("/autor/{autor}")
    public List<LivroDTO> obterPorAutor(@PathVariable String autor) {
        return servico.listaObrasDoAutor(autor);
    }

    @GetMapping("/obra/{titulo}")
    public List<LivroDTO> obterPorTitulo(@PathVariable String titulo) {
        return servico.listaObra(titulo);
    }

    @GetMapping("/idioma/{idioma}")
    public List<LivroDTO> obterPorIdioma(@PathVariable String idioma) {
        return servico.listaObraDoIdioma(idioma);
    }

    @GetMapping("/popular-banco")
    public List<LivroDTO> popularBanco() {
        return servico.popularBanco();
    }
}
