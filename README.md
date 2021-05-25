# Desafio Backend Java da Wine

Este repositório representa a solução do **Desafio Backend Java** da Wine (wine.com.br).

Ela foi desenvolvida em formato de microsserviço utilizando a linguagem Java 11 e também como base o framework Spring
Boot 2.5.0.

## Recursos da solução

- Suporte a Docker
- Geração de pacote JAR usando GitHub CI
- OpenAPI/Swagger
- Testes automatizados

## Construção e execução da aplicação

### Utilizando um computador convencional

Para executar os passos abaixo são necessárias as seguintes bibliotecas e ferramentas instaladas:

- Java JDK 11
- Maven 3.6 ou superior
- PostgreSQL 9.6+

Com estas dependências instaladas, siga os seguintes passos:

1. Defina os valores das seguintes variáveis de ambiente no seu sistema para acesso ao banco de dados:
    1. **DB_HOST**: endereço da instância do banco de dados
    2. **DB_NAME**: nome do banco de de dados
    3. **DB_USERNAME**: nome de usuário do banco de dados
    4. **DB_PASSWORD**: senha de acesso ao banco de dados
2. Navegue até o diretório do projeto utilizando um terminal/console do sistema e execute o comando `mvn package`
3. Navegue até o diretório `target` e execute a aplicação com o comando `java -jar desafio-backend-wine-1.0.0.jar`
4. Ao fim do carregamento da aplicação, acesse os endpoints dispostos no endereço `http://localhost:8080/`

### Utilizando Docker Compose

Para executar os passos abaixo são necessárias as seguintes bibliotecas e ferramentas instaladas:

- Docker v20.00+
- Docker Compose

Com esta dependência instalada, siga os seguintes passos:

1. Navegue até o diretório do projeto utilizando um terminal/console do sistema e execute o comando `docker-compose up`
2. Ao fim do processo de construção dos containers Docker, acesse os endpoints dispostos no
   endereço `http://localhost:8080/`

## Endpoints

Esta aplicação possui suporte a OpenAPI e Swagger. Para acessar a página de documentação dos endpoints, rode a aplicação
e acesse o endereço `http://localhost:8080/swagger-ui.html`.


