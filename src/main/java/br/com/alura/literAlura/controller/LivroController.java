package br.com.alura.literAlura.controller;

import br.com.alura.literAlura.dto.LivroDTO;
import br.com.alura.literAlura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService servico;

    @GetMapping("/")
    public String principal() {
        return "index";
    }

    @GetMapping("/filtrar")
    @ResponseBody
    public List<LivroDTO> filtrarLivros(@RequestParam(required = false) String genero,
                                        @RequestParam(required = false) String autor,
                                        @RequestParam(required = false) String idioma,
                                        @RequestParam(required = false) String titulo) {
        if (genero != null && !genero.isEmpty()) {
            return servico.listaPorGenero(genero);
        } else if (autor != null && !autor.isEmpty()) {
            return servico.listaObrasDoAutor(autor);
        } else if (idioma != null && !idioma.isEmpty()) {
            return servico.listaObraDoIdioma(idioma);
        } else if (titulo != null && !titulo.isEmpty()) {
            return servico.listaObra(titulo);
        } else {
            return servico.listaDadosDisponiveisNoBanco();
        }
    }

    @GetMapping("/todos")
    @ResponseBody
    public List<LivroDTO> mostrarTodos() {
        return servico.listaDadosDisponiveisNoBanco();
    }

    @GetMapping("/top5")
    @ResponseBody
    public List<LivroDTO> obterTop5() {
        return servico.listaTop5();
    }

    @GetMapping("/genero/{genero}")
    @ResponseBody
    public List<LivroDTO> obterPorGenero(@PathVariable String genero) {
        return servico.listaPorGenero(genero);
    }

    @GetMapping("/autor/{autor}")
    @ResponseBody
    public List<LivroDTO> obterPorAutor(@PathVariable String autor) {
        return servico.listaObrasDoAutor(autor);
    }

    @GetMapping("/obra/{titulo}")
    @ResponseBody
    public List<LivroDTO> obterPorTitulo(@PathVariable String titulo) {
        return servico.listaObra(titulo);
    }

    @GetMapping("/idioma/{idioma}")
    @ResponseBody
    public List<LivroDTO> obterPorIdioma(@PathVariable String idioma) {
        return servico.listaObraDoIdioma(idioma);
    }

    @GetMapping("/popular-banco")
    @ResponseBody
    public List<LivroDTO> popularBanco() {
        return servico.popularBanco();
    }

}
