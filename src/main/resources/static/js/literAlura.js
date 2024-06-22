document.addEventListener('DOMContentLoaded', () => {
    carregarLivros();
    document.getElementById('filtro-form').addEventListener('submit', event => {
        event.preventDefault();
        const genero = document.getElementById('genero').value;
        const autor = document.getElementById('autor').value;
        const idioma = document.getElementById('idioma').value;
        const titulo = document.getElementById('titulo').value;

        const numFiltrosSelecionados = [genero, autor, idioma, titulo].filter(value => value !== '').length;

        if (numFiltrosSelecionados > 1) {
            alert('Por favor, selecione apenas um filtro por vez.');
        } else {
            filtrarLivros(genero, autor, idioma, titulo);
        }
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

function filtrarLivros(genero, autor, idioma, titulo) {
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

    const tituloContainer = document.createElement('h2');
    tituloContainer.innerHTML = `<i class="ph ph-books"></i> ${tituloSecao}`;

    const secaoLivros = document.createElement('div');
    secaoLivros.classList.add('secao_livros');

    if (data.length === 0) {
        secaoLivros.innerHTML = '<p class="nenhum-livro">Nenhum livro encontrado.</p>';
    } else {
        data.forEach(livro => {
            const livroDiv = document.createElement('div');
            livroDiv.classList.add('livro');

            const tituloP = document.createElement('p');
            tituloP.classList.add('livro_titulo');
            tituloP.textContent = `${livro.resultados[0].titulo}`;

            const autorP = document.createElement('p');
            autorP.classList.add('livro_autor');
            autorP.textContent = `${livro.resultados[0].autores[0].nome}`;

            const downloadsP = document.createElement('p');
            downloadsP.classList.add('livro_downloads');
            downloadsP.innerHTML = `<span><i class="ph ph-download-simple"></i></span> ${livro.resultados[0].numeroDownloads}`;

            const generoP = document.createElement('p');
            generoP.classList.add('livro_genero', livro.resultados[0].generos[0].replace(/\s+/g, '-').toLowerCase());
            generoP.textContent = `${livro.resultados[0].generos[0]}`;

            const imagem = document.createElement('img');
            imagem.classList.add('livro_imagem');
            imagem.src = livro.resultados[0].formatos['image/jpeg'];
            imagem.alt = 'Capa do Livro';

            livroDiv.appendChild(tituloP);
            livroDiv.appendChild(autorP);
            livroDiv.appendChild(downloadsP);
            livroDiv.appendChild(generoP);
            livroDiv.appendChild(imagem);

            secaoLivros.appendChild(livroDiv);
        });
    }

    livrosContainer.appendChild(tituloContainer);
    livrosContainer.appendChild(secaoLivros);
}
