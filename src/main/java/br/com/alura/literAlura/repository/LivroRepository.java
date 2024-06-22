package br.com.alura.literAlura.repository;

import br.com.alura.literAlura.dto.LivroDTO;
import br.com.alura.literAlura.model.GeneroLivro;
import br.com.alura.literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findTop5ByOrderByNumeroDownloadsDesc();
    List<Livro> findByGeneroIgnoreCase(String genero);
    List<Livro> findByAutorContainingIgnoreCase(String autor);

    @Query("SELECT l FROM Livro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Livro> buscarPorTitulo(String titulo);

    @Query("SELECT l FROM Livro l WHERE l.idioma = UPPER(:idioma)")
    List<Livro> buscarPorIdioma(String idioma);
}
