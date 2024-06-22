package br.com.alura.literAlura.model;

import java.text.Normalizer;

public enum GeneroLivro {
    FICCAO("Ficção"),
    AVENTURA("Aventura"),
    DRAMA("Drama");

    private final String nome;

    GeneroLivro(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static GeneroLivro porNome(String nome) {
        String nomeNormalizado = normalizarString(nome);

        for (GeneroLivro genero : GeneroLivro.values()) {
            String nomeEnumNormalizado = normalizarString(genero.getNome());

            if (nomeEnumNormalizado.equalsIgnoreCase(nomeNormalizado)) {
                return genero;
            }
        }
        return null;
    }

    private static String normalizarString(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();
    }
}
