package br.com.alura.literAlura.controller;

import br.com.alura.literAlura.dto.LivroDTO;
import br.com.alura.literAlura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService servico;

    @GetMapping("/popular-banco")
    public List<LivroDTO> popularBanco() {
        return servico.popularBanco();
    }
}
