# Gerenciador de Estoque - API

Recebi a tarefa de criar um README completo e voltado para desenvolvedores júnior e recrutadores. Abaixo está um guia
prático, com visão geral do projeto, funcionalidades, tecnologias utilizadas, instruções de setup/execução/testes, e
informações sobre a proteção que evita que dados sensíveis sejam commitados.

Sumário

- Visão geral
- Principais funcionalidades
- Tecnologias e dependências
- Arquitetura e organização do código
- Como rodar (local / Docker)
- Banco de dados, migrations e seed
- Endpoints principais (resumo)
- Autenticação (JWT)
- Testes
- Segurança: proteção contra commits de arquivos sensíveis
- Comandos úteis
- Contribuição
- Contato

Visão geral
Este repositório contém uma API REST em Java (Spring Boot) para gerenciamento de estoque. O objetivo é fornecer
operações CRUD para produtos e gerenciamento de usuários com autenticação baseada em JWT. O projeto utiliza SQLite como
banco de dados embarcado (fácil para desenvolvimento) e tem suporte a execução via Docker.

Público-alvo

- Desenvolvedores júnior: instruções passo a passo para rodar e entender o código
- Recrutadores / revisores técnicos: visão geral das responsabilidades, tecnologias e endpoints

Principais funcionalidades

- Autenticação (login com JWT)
- CRUD de produtos (criar, listar, buscar por id, atualizar, remover)
- Gerenciamento de usuários: registro, alteração de nome/e-mail/senha/role, soft delete e restore
- Migrations para criar tabelas e script de seed para popular produtos
- Documentação OpenAPI (Swagger)

Tecnologias e dependências

- Java 17+ (projeto empacotado usando imagem Temurin 25 JRE no Dockerfile)
- Spring Boot (Web, Security, Validation, JPA/Hibernate)
- SQLite (via JDBC) para ambiente de desenvolvimento
- Flyway/SQL migrations (migrations SQL em src/main/resources/db/migration)
- Maven (wrapper incluído: `mvnw`, `mvnw.cmd`)
- Swagger / springdoc-openapi para documentação interativa
- Docker / docker-compose (opções para rodar em container)

Organização do código (resumo)

- `src/main/java/com/diego_ramos/gerenciador_estoque/`
    - `controller/` — endpoints REST (AuthController, ProductController, UserController)
    - `service/` — regras de negócio
    - `repository/` — acesso aos dados
    - `domain/` — entidades (Product, User, BaseEntity)
    - `dto/` — objetos de transporte (requests/responses)
    - `config/` — configuração de segurança, JWT, OpenAPI
- `src/main/resources/`
    - `application.properties` — configurações (porta, datasource, jwt)
    - `db/migration/` — SQL para criar tabelas (V1__..., V2__...)
    - `db/seed_products.sql` — seed opcional para popular produtos
- `Dockerfile`, `docker-compose.yml` — passos e orquestração para container

Configurações importantes

- Context-path: `/api` (veja `application.properties` -> `server.servlet.context-path=/api`)
- Porta padrão: `8080` (configurável via `SERVER_PORT`)
- Datasource (default no projeto): SQLite em `/app/data/database.db` (no host mapeado: `./data/database.db`)
- JWT: `jwt.secret` e `jwt.expiration` configuráveis via variáveis de ambiente (ex.: `.env`)

Como rodar
Requisitos locais

- Java 17+ (apenas para rodar sem docker)
- Maven (opcional se usar o wrapper) — use o wrapper fornecido
- Docker Desktop (opcional para execução em container)

1) Rodar via Maven (modo desenvolvimento)

- No Windows (usando wrapper):

  mvnw.cmd spring-boot:run

- No Linux/macOS:

  ./mvnw spring-boot:run

A aplicação ficará disponível em: http://localhost:8080/api
Swagger UI: http://localhost:8080/api/swagger-ui.html

2) Empacotar e rodar JAR

- Gerar o JAR:

  mvnw.cmd -DskipTests package

- Executar:

  java -jar target/*.jar

3) Usando Docker (recomendado para consistência)

- Build e run com docker-compose:

  docker compose up --build

  ou (Docker clássico):

  docker build -t gerenciador_estoque:dev .
  docker run -d --name gerenciador_estoque -p 8080:8080 -v C:\caminho\para\seu\projeto\data:/app/data
  gerenciador_estoque:dev

Observação: ajuste o caminho do volume no Windows ao usar `docker run -v` para mapear `./data` do projeto para
`/app/data` do container (veja `docker-compose.yml` que já mapeia `./data:/app/data`). Também existe `docs/DOCKER.md`
com exemplos práticos.

Banco de dados, migrations e seed

- Migrations SQL estão em `src/main/resources/db/migration/`:
    - V1__create_product_table.sql — tabela `product`
    - V2__create_users_table.sql — tabela `users`
- O banco default para dev é SQLite e o arquivo fica em `data/database.db` (incluído no repositório para conveniência)
- Seed para popular produtos: `src/main/resources/db/seed_products.sql` — pode ser aplicado manualmente com sqlite3:

  sqlite3 data\database.db < src\main\resources\db\seed_products.sql

Endpoints principais (resumo)
Base da API: http://localhost:8080/api

1) Autenticação

- POST /api/auth/login
    - Body (JSON): { "email": "...", "password": "..." }
    - Retorna: token JWT (use em Authorization: Bearer <token>)

2) Produtos

- POST /api/products — cria produto (autenticado)
- GET /api/products — lista produtos
- GET /api/products/{id} — busca por ID
- PATCH /api/products/{id} — atualiza
- DELETE /api/products/{id} — remove (hard ou soft depende da implementação de service)

3) Usuários

- POST /api/users/register — registra um novo usuário
- PATCH /api/users/{id}/name — atualiza nome (admin ou dono)
- PATCH /api/users/{id}/email — atualiza email
- PATCH /api/users/{id}/password — atualiza senha
- PATCH /api/users/{id}/role — atualiza role (MANAGER/ADMIN)
- DELETE /api/users/{id} — desativa (soft delete) — admin
- PATCH /api/users/{id}/restore — restaura usuário desativado — admin

Autenticação e autorização

- JWT é usado para autenticação. Após `POST /auth/login` receba um token JWT e adicione o header nas requisições
  protegidas:

  Authorization: Bearer <TOKEN>

- Algumas rotas exigem permissões (roles): ADMIN, MANAGER, etc. As anotações `@PreAuthorize` são utilizadas para
  proteção de rotas.

Exemplo rápido (curl)

- Login:

  curl -X POST "http://localhost:8080/api/auth/login" -H "Content-Type: application/json" -d "{\"email\":
  \"admin@example.com\",\"password\":\"senha\"}"

- Requisição com token:

  curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/products

Testes

- Testes unitários/integrados (se existirem) podem ser executados com Maven:

  mvnw.cmd test

Segurança: bloqueio de commits com arquivos sensíveis

- Este repositório já inclui um workflow GitHub Actions em `.github/workflows/block-secrets.yaml` que falhará em
  pushes/pull requests se arquivos sensíveis forem detectados (padrões como `.env`, arquivos `.pem`, `.key`,
  `credentials.json`, `config.json`).
- Exemplo: o arquivo `.github/workflows/block-secrets.yaml` procura por padrões e aborta o job caso existam arquivos
  sensíveis versionados.

Boas práticas locais para evitar vazamento

- Nunca commit: `.env`, chaves privadas (`*.pem`, `*.key`) ou arquivos com credenciais
- Adicione ao `.gitignore` arquivos locais e ambiente (por exemplo: `.env`, `data/database.db` se for sensível no seu
  fluxo)
- Use variáveis de ambiente para segredos em CI/CD

Arquivo `.env` de exemplo

- Existe um `.env` de exemplo no repositório que define `JWT_SECRET` e `JWT_EXPIRATION`. Em produção, mantenha esse
  arquivo fora do repositório e use o secret manager da sua plataforma.

  JWT_SECRET=seu_super_secreto
  JWT_EXPIRATION=86400000

Comandos úteis

- Build e testes (Windows):

  mvnw.cmd -DskipTests package
  mvnw.cmd test

- Rodar com docker-compose:

  docker compose up --build

- Aplicar seed manualmente (SQLite):

  sqlite3 data\database.db < src\main\resources\db\seed_products.sql

Contribuição

- Abra uma issue descrevendo a proposta ou bug
- Crie uma branch com nome explicativo (`feature/`, `fix/`)
- Faça PR com descrição clara e testes quando aplicável

Contato

- Autor do repositório: (verifique o commit/README do projeto para contato)

Observações finais

- Este README foi pensado para ser uma referência prática e direta. Se quiser, posso:
    - Gerar exemplos mais completos de requests (Postman collection ou exemplos curl para cada endpoint)
    - Criar um `.env.example` e um `.gitignore` sugerido
    - Adicionar instruções para CI/CD (deploy em Heroku, AWS, etc.)

---

Arquivo de workflow que bloqueia commits sensíveis: `.github/workflows/block-secrets.yaml`
(É recomendável manter esse workflow ativo e ajustar o padrão de arquivos sensíveis conforme sua política de segurança.)

