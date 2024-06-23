# 📚 LiterAlura

O projeto LiterAlura é uma aplicação web que permite aos usuários buscar e visualizar livros de diversas categorias, autores e idiomas, utilizando dados da API do Gutendex. Abaixo estão os tópicos detalhados sobre como configurar e utilizar a aplicação.

<img src="https://github.com/LarissaSL/LiterAlura/assets/112571317/4b78be83-1347-4aab-980a-c119885c0eeb" width="1280">

## 📌 Primeiros Passos

### 1. Configuração do Banco de Dados

#### 1.1. Instalação do PostgreSQL
- Certifique-se de ter o PostgreSQL instalado em seu computador.

#### 1.2. Configuração do Banco de Dados
1. Abra seu pgAdmin.
2. Clique em Servers.
3. Em Databases, clique com o botão direito > Create > Database > No campo database insira o nome `liter_alura`.

#### 1.3. Configuração do Banco na Aplicação
1. Em `application.properties`, troque os seguintes campos pelos que você configurou ao instalar seu PostgreSQL:
   <img src="https://github.com/LarissaSL/LiterAlura/assets/112571317/7f229f8a-dc62-47ed-b80f-7104f49131ec" width="500">

#### 1.4. Configuração Extra
- Caso queira visualizar no console as queries, basta descomentar as seguintes linhas em `application.properties`:
    ```properties
    # spring.jpa.show-sql=true
    # spring.jpa.format-sql=true
    ```

### 🖥️ 2. Utilizando a Aplicação

#### 2.1. Inicialização
- Inicie o `LiterAluraApplication` pela sua IDE.

#### 2.2. Popular o Banco de Dados
1. Acesse a URL: [http://localhost:8080/livro/popular-banco](http://localhost:8080/livro/popular-banco).
    - OBS.: Pode demorar um pouco, mas é a aplicação consultando a API do Gutendex e populando o banco. Note que no console da IDE ele mostra os livros sendo registrados e, no final, a página te devolverá um JSON com todos os livros registrados.

#### 2.3. Acessar a Aplicação
- Acesse a URL: [http://localhost:8080/livro/](http://localhost:8080/livro/).
- Agora é só utilizar os filtros.

### 📖 3. Tabela de Filtros

| Filtro  | Descrição |
|---------|-----------|
| Gênero  | Filtra os livros por gênero |
| Autor   | Filtra os livros por autor, aceitando nome, sobrenome ou uma parte apenas do nome |
| Idioma  | Filtra os livros por idioma |
| Título  | Filtra os livros por título, aceitando o título da obra inteira ou parte apenas |

### 📖 4. Arquitetura Resultante
<img src="https://github.com/LarissaSL/LiterAlura/assets/112571317/4aca2dbe-6908-42fa-bfef-0dd87f04f094" width="300">

## 🔧⚙️ Verificação via Postman ou APIDog

### URLs para Verificação

- [http://localhost:8080/livro/](http://localhost:8080/livro/) - Para Listar os dados utilizando o Front e Back em conjunto.
- [http://localhost:8080/livro/popular-banco](http://localhost:8080/livro/popular-banco) - Para popular o banco ou ver os registros já salvos.
- [http://localhost:8080/livro/autor/InserirNomeDoAutor](http://localhost:8080/livro/autor/InserirNomeDoAutor) - Para listar as obras desse autor.
- [http://localhost:8080/livro/genero/InserirGenero](http://localhost:8080/livro/genero/InserirGenero) - Para listar as obras desse gênero.
- [http://localhost:8080/livro/idioma/InserirIdioma](http://localhost:8080/livro/idioma/InserirIdioma) - Para listar as obras desse idioma.
- [http://localhost:8080/livro/top5](http://localhost:8080/livro/top5) - Para listar as 5 obras mais baixadas.
- [http://localhost:8080/livro/obra/InserirNomeDoLivro](http://localhost:8080/livro/obra/InserirNomeDoLivro) - Para listar as obras com esse título.

## ⌨️ Desenvolvimento

### Passos seguidos para o Desenvolvimento

1. Desenvolvi a arquitetura.
2. Criação do Controller.
3. Primeira solução para o back-end (deu errado).
4. Testes via APIDog.
5. Segunda solução para o back-end com DTOs e já realizando consultas.
6. Testes via APIDog.
7. Criação do index e primeira integração do back-end com o front-end (mostrar todos os livros).
8. Criação do JavaScript para deixar dinâmico (chamando todos os livros e Top 5) e utilizar os filtros via controller, devolvendo os livros correspondentes.
9. Desenvolvimento do CSS.
10. Testes finais com back-end e front-end integrados.

## 📚 Tecnologias Utilizadas

<p align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" alt="Java" width="50" height="50"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" alt="Spring" width="50" height="50"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" alt="PostgreSQL" width="50" height="50"/>
  <img src="https://cdn-icons-png.flaticon.com/512/2160/2160724.png" alt="API Gutendex" width="50" height="50"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" alt="JavaScript" width="50" height="50"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg" alt="HTML" width="50" height="50"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg" alt="CSS" width="50" height="50"/>
</p>

### Versões

- **Java**: 17
- **Spring**: Spring Framework
- **PostgreSQL**: PostgreSQL
- **API**: Gutendex
- **JavaScript**: JavaScript
- **HTML**: HTML
- **CSS**: CSS

## Materiais de Apoio Utilizados 🎥

Aqui estão alguns vídeos e cursos que utilizei como apoio durante o desenvolvimento:

- [Curso Alura](https://cursos.alura.com.br/course/java-trabalhando-lambdas-streams-spring-framework) **Java: trabalhando com lambdas, streams e Spring Framework**
- [Curso Alura](https://cursos.alura.com.br/course/java-persistencia-dados-consultas-spring-data-jpa) **Java: persistência de dados e consultas com Spring Data JPA**
- [Curso Alura](https://cursos.alura.com.br/course/java-criando-primeira-api-conectando-front) **Java: criando sua primeira API e conectando ao front**
- [Video do Youtube](https://www.youtube.com/watch?v=c39YfuI7gR4) **JavaScript - Adicionar elementos ao DOM**
- [Video do Youtube](https://youtu.be/KUUT50sgaAs) **DTO - Entendendo o que é Data Transfer Object | Dias de Dev**
- [Video do Youtube](https://www.youtube.com/watch?v=qsIkWczoan8) **Diferença entre @Controller e @RestController do Spring MVC**

## 🎬 Simulação 

Aqui está uma simulação da Aplicação em funcionamento:

https://github.com/LarissaSL/LiterAlura/assets/112571317/4a8afba0-018e-4355-8736-575b40751094


Espero que este guia ajude você a configurar e utilizar o LiterAlura. Se você tiver qualquer dúvida ou problema, sinta-se à vontade para entrar em contato.

## ⚠️ Copyright

- Imagens de livros do Header por Freepik.
- Ícones por Phosphor Icons.
- Back-end e front-end desenvolvidos por LarissaSL (Eu 😄).
