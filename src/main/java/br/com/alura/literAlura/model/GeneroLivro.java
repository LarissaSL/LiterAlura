package br.com.alura.literAlura.model;
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

    public static GeneroLivro fromNome(String nome) {
        for (GeneroLivro genero : GeneroLivro.values()) {
            if (genero.getNome().equalsIgnoreCase(nome)) {
                return genero;
            }
        }
        return null;
    }
}