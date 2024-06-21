package br.com.alura.literAlura.repository;

import br.com.alura.literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
