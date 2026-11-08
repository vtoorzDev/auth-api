# Auth API

API REST desenvolvida com **Java e Spring Boot** para autenticação e gerenciamento de usuários e administradores, utilizando Spring Security e JWT para autenticação e controle de acesso.

O projeto permite realizar cadastro e login, gerar tokens JWT e proteger endpoints de acordo com as permissões de cada tipo de usuário.

##  Tecnologias utilizadas

- **Java 26**
- **Spring Boot 4.1.2**
- **Spring Security 7**
- **Spring Data JPA**
- **Hibernate**
- **MySQL 8**
- **JWT (JSON Web Token)**
- **OAuth2 Resource Server**
- **Flyway**
- **Bean Validation**
- **Maven**

##  Funcionalidades

### Autenticação e segurança

- Cadastro de usuários.
- Cadastro de administradores.
- Login de usuários.
- Login de administradores.
- Geração de tokens JWT.
- Validação de tokens JWT.
- Autenticação utilizando Bearer Token.
- Proteção de endpoints com Spring Security.
- Controle de acesso baseado em autoridades (Roles).
- Senhas armazenadas com criptografia BCrypt.
- Separação dos processos de autenticação de usuários e administradores.

### Gerenciamento de usuários

- Cadastro de usuários.
- Listagem de usuários com paginação.
- Atualização de usuários.
- Exclusão de usuários.
- Contagem de usuários cadastrados.

##  Controle de acesso

A aplicação utiliza Spring Security para controlar o acesso aos endpoints.

Existem dois tipos de usuários:

| Autoridade | Permissões |
|---|---|
| ADMIN | Acesso administrativo às rotas protegidas |
| USUARIO | Acesso às funcionalidades permitidas para usuários comuns |

A autenticação é realizada por meio de tokens JWT. Após o login, o cliente recebe um token que deve ser enviado nas requisições protegidas através do cabeçalho HTTP:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

##  Endpoints

### Usuários

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/usuario/cadastro` | Cadastrar usuário |
| POST | `/usuario/login` | Realizar login |
| GET | `/usuario/listar-todos` | Listar usuários com paginação |
| PUT | `/usuario/atualizar-usuario/{id}` | Atualizar usuário |
| DELETE | `/usuario/deletar-usuario/{id}` | Excluir usuário |
| GET | `/usuario/usuarios-cadastrados` | Contar usuários cadastrados |

### Paginação

O endpoint de listagem utiliza paginação do Spring Data.

Exemplo:

```http
GET /usuario/listar-todos?page=0&size=10
```

Parâmetros:
- `page`: número da página.
- `size`: quantidade de registros por página.

### Administradores

- Cadastro de administrador.
- Login de administrador.

> Adicione aqui os caminhos exatos dos endpoints de administrador.

##  Autenticação JWT

O projeto utiliza JWT para autenticar as requisições e identificar as autoridades do usuário autenticado.

O fluxo de autenticação funciona da seguinte maneira:

1. O usuário ou administrador envia suas credenciais para o endpoint de login correspondente.
2. O Spring Security realiza a autenticação.
3. Após a autenticação bem-sucedida, a aplicação gera um token JWT.
4. O token é retornado ao cliente.
5. O cliente utiliza o token nas próximas requisições.
6. O Spring Security valida o token e verifica as permissões necessárias para acessar o endpoint.

##  Banco de dados

O projeto utiliza MySQL para armazenar os dados da aplicação.

O Flyway é responsável pelo versionamento e gerenciamento das migrações do banco de dados, mantendo a estrutura das tabelas organizada durante a evolução do projeto.

Crie o banco de dados:

```sql
CREATE DATABASE auth;
```

##  Como executar o projeto

### Pré-requisitos

Antes de executar a aplicação, você precisa ter instalado:

- Java 26 ou compatível com a configuração do projeto.
- MySQL 8.
- Maven.
- Git.

### 1. Clone o repositório

```bash
git clone https://github.com/SEU_USUARIO/auth.git
```

### 2. Entre na pasta do projeto

```bash
cd auth
```

### 3. Configure o banco de dados

Crie o banco `auth` no MySQL e configure as credenciais da conexão no arquivo `application.properties` ou através de variáveis de ambiente.

Exemplo de configuração:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

api.security.token.secret=${JWT_SECRET}
```

Configure as variáveis de ambiente com os dados do seu ambiente local.

**Importante:** não compartilhe senhas do banco de dados ou o segredo utilizado para assinar os tokens JWT.

### 4. Execute a aplicação

No Windows, utilizando o Maven Wrapper:

```bash
mvnw.cmd spring-boot:run
```

Ou, caso tenha o Maven instalado:

```bash
mvn spring-boot:run
```

A aplicação será iniciada na porta `8080`, salvo configuração diferente.

O Flyway executará automaticamente as migrações pendentes durante a inicialização.

##  Testes das requisições

As requisições podem ser realizadas utilizando ferramentas como:

- Postman
- Insomnia

Para acessar endpoints protegidos, selecione o tipo de autenticação **Bearer Token** e informe o JWT retornado pelo login.

##  Organização do projeto

O projeto segue uma organização por responsabilidades, separando componentes de autenticação, regras de negócio, persistência e exposição dos endpoints.

Estrutura simplificada:

```text
src/
 └── main/
     ├── java/
     │   └── com/auth/
     │       ├── config/
     │       ├── controller/
     │       ├── dtos/
     │       │   ├── request/
     │       │   └── response/
     │       ├── entity/
     │       ├── exception/
     │       ├── repository/
     │       └── service/
     │
     └── resources/
         ├── db/
         │   └── migration/
         └── application.properties
```

##  Objetivo do projeto

Este projeto foi desenvolvido com o objetivo de praticar e aprofundar conhecimentos em desenvolvimento Backend com Java, trabalhando conceitos de autenticação, autorização, segurança de APIs REST, persistência de dados e organização de aplicações com Spring Boot.

##  Autor

**Victor Araújo**

- GitHub: [@vtoorzDev](https://github.com/vtoorzDev)
- LinkedIn: [Victor Araújo](https://linkedin.com/in/victornascimentoaraujo/)
