### Microsserviço Customer


### 💻 Sobre o projeto
Com esse microsserviço será possivel criar, editar, excluir e consultar um cliente.

### ⚙️ Funcionalidades

- [x] Clientes:
  - [x] Adicionar novo cliente;
  - [x] Editar cliente;
  - [x] Excluir cliente;
  - [x] Adicionar endereço ao cliente;
  - [x] Consultar clientes por filtros;


### 🛠 Técnologias e padrão utilizadas

- Arquitetura padrão MVC;
- Spring Boot;
- Java 11;
- Maven;
- H2 Database;
- Lombok;
- Swagger;

### 🧭 Rodando a aplicação

#### Clonar o repositório do projeto

```sh
git clone https://github.com/LuaneASantos/ms-customer.git
```
Importar o projeto na IDE de sua preferência.

#### Dentro do diretório do projeto, buildar com o Maven
```sh
mvn clean install
```

O projeto baixará as dependências necessárias e buildará com sucesso. Caso não complete com sucesso, verifique o log do build para encontrar possíveis erros.

#### Documentação da API
A documentação da API é feita através do swagger, e quando a aplicação estiver rodando em ambiente local você pode acessá-la pelo [link](http://localhost:8080/swagger-ui/index.html#/)
