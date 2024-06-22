document.addEventListener('DOMContentLoaded', () => {
    carregarLivros();
    document.getElementById('filtro-form').addEventListener('submit', event => {
        event.preventDefault();
        filtrarLivros();
    });
});

function carregarLivros() {
    fetch('/livro/top5')
        .then(response => response.json())
        .then(data => {
            exibirLivros(data, 'Top 5 Livros');
            return fetch('/livro/todos');
        })
        .then(response => response.json())
        .then(data => exibirLivros(data, 'Todos os Livros'))
        .catch(error => console.error('Erro ao carregar livros:', error));
}

function filtrarLivros() {
    const genero = document.getElementById('genero').value;
    const autor = document.getElementById('autor').value;
    const idioma = document.getElementById('idioma').value;
    const titulo = document.getElementById('titulo').value;

    const livrosContainer = document.getElementById('livrosContainer');
    livrosContainer.innerHTML = '';

    if (!genero && !autor && !idioma && !titulo) {
        carregarLivros();
    } else {
        const url = `/livro/filtrar?genero=${genero}&autor=${autor}&idioma=${idioma}&titulo=${titulo}`;
        fetch(url)
            .then(response => response.json())
            .then(data => exibirLivros(data, 'Livros Filtrados'))
            .catch(error => console.error('Erro ao filtrar livros:', error));
    }
}

function exibirLivros(data, tituloSecao) {
    const livrosContainer = document.getElementById('livrosContainer');
    const secaoLivros = document.createElement('div');
    secaoLivros.classList.add('secao_livros');
    secaoLivros.innerHTML = `<h2>${tituloSecao}</h2>`;

    if (data.length === 0) {
        secaoLivros.innerHTML += '<p class="nenhum-livro">Nenhum livro encontrado.</p>';
    } else {
        data.forEach(livro => {
            const livroDiv = document.createElement('div');
            livroDiv.classList.add('livro');

            const tituloP = document.createElement('p');
            tituloP.classList.add('livro_titulo');
            tituloP.textContent = `Título: ${livro.resultados[0].titulo}`;

            const autorP = document.createElement('p');
            autorP.classList.add('livro_autor');
            autorP.textContent = `Autor: ${livro.resultados[0].autores[0].nome}`;

            const generoP = document.createElement('p');
            generoP.classList.add('livro_genero', livro.resultados[0].generos[0].replace(/\s+/g, '-').toLowerCase());
            generoP.textContent = `Gênero: ${livro.resultados[0].generos[0]}`;

            const imagem = document.createElement('img');
            imagem.classList.add('livro_imagem');
            imagem.src = livro.resultados[0].formatos['image/jpeg'];
            imagem.alt = 'Capa do Livro';

            livroDiv.appendChild(tituloP);
            livroDiv.appendChild(autorP);
            livroDiv.appendChild(generoP);
            livroDiv.appendChild(imagem);

            secaoLivros.appendChild(livroDiv);
        });
    }

    livrosContainer.appendChild(secaoLivros);
}
