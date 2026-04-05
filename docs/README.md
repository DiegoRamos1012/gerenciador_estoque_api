Gerenciador de Estoque — API

Visão geral rápida
------------------
Esta é uma API REST em Java (Spring Boot) para gerenciar um catálogo/estoque de produtos. Conta com:

- Autenticação por JWT;
- CRUD de produtos;
- Gerenciamento de usuários (registro, atualização, soft delete e restore);
- Banco SQLite embarcado para desenvolvimento;
- Documentação Swagger (OpenAPI).

Por que este repositório é útil

- Fácil de rodar localmente e em Docker;
- Estrutura didática (controllers, services, repositories, domain, dto);
- Migrations e seed SQL para iniciar com dados de exemplo;
- Workflow para ajudar a bloquear commits de segredos.

Quick start (2 minutos)
-----------------------
Pré-requisitos:

- Java 17+ (se não usar Docker)
- Docker Desktop (recomendado)
- Maven (opcional — use o wrapper `mvnw` / `mvnw.cmd`)

1) Com Docker (recomendado)

```bash
docker compose up --build
```

A API ficará disponível em:

- Base: http://localhost:8080/api
- Swagger: http://localhost:8080/api/swagger-ui.html

2) Sem Docker (desenvolvimento)

- Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

- macOS / Linux:

```bash
./mvnw spring-boot:run
```

3) Gerar JAR e executar

- Build:

```powershell
.\mvnw.cmd -DskipTests package
```

- Executar:

```bash
java -jar target/*.jar
```

Banco de dados, migrations e seed
---------------------------------

- Migrations em `src/main/resources/db/migration/`:
    - `V1__create_product_table.sql`
    - `V2__create_users_table.sql`
- Banco dev (SQLite): `data/database.db` (no container: `/app/data/database.db`)
- Seed de exemplo: `src/main/resources/db/seed_products.sql`
    - Aplicação manual (SQLite):

```bash
sqlite3 data\database.db < src\main\resources\db\seed_products.sql
```

Endpoints principais (resumo)
-----------------------------
Base: http://localhost:8080/api

Autenticação

- POST /api/auth/login  
  Body: `{ "email": "...", "password": "..." }`  
  Retorna: token JWT (use em header `Authorization: Bearer <TOKEN>`)

Produtos (protegidos por JWT)

- POST /api/products — cria produto
- GET /api/products — lista produtos
- GET /api/products/{id} — busca por id (UUID)
- PATCH /api/products/{id} — atualiza parcialmente
- DELETE /api/products/{id} — remove

Usuários

- POST /api/users/register — cria usuário
- PATCH /api/users/{id}/name — atualiza nome (admin ou dono)
- PATCH /api/users/{id}/email — atualiza e-mail
- PATCH /api/users/{id}/password — atualiza senha
- PATCH /api/users/{id}/role — atualiza role (MANAGER/ADMIN) — protegido
- DELETE /api/users/{id} — desativa usuário (soft delete) — admin
- PATCH /api/users/{id}/restore — restaura usuário — admin

Exemplos rápidos (curl)
-----------------------
Login:

```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"senha"}'
```

Listar produtos com token:

```bash
curl "http://localhost:8080/api/products" \
  -H "Authorization: Bearer <SEU_TOKEN_AQUI>"
```

Onde começar no código (para júnior)
------------------------------------

- Controllers: `src/main/java/.../controller/` — `AuthController`, `ProductController`, `UserController`
- Services: `src/main/java/.../service/` — `ProductService`, `UserService`
- Domain/Entities: `src/main/java/.../domain/` — `Product`, `User`, `BaseEntity`
- Config / Segurança: `src/main/java/.../config/` — `SecurityConfig`, `JwtFilter`, `JwtConfig`, `OpenApiConfig`
- DTOs: `src/main/java/.../dto/` — veja campos e validações esperadas nas requisições

Sugestões rápidas para leitura:

- Comece pelo `ProductController` para entender os endpoints e depois abra `ProductService` para a lógica.
- As anotações `@PreAuthorize` no `UserController` mostram restrições por role.
- As migrations mostram o esquema do DB; o seed traz dados para testar.

Segurança: evitar commits de segredos
------------------------------------

- O repositório inclui um workflow em `.github/workflows/block-secrets.yaml` que procura por padrões como `.env`,
  `*.pem`, `*.key`, `config.json`, `credentials.json` e faz o job falhar caso estejam versionados.
- Atenção: há um `.env` no repositório atual contendo `JWT_SECRET`. Em ambientes reais, remova esse `.env` do
  repositório e use um `.env.example` + variáveis de ambiente/secret manager.

`.env.example` (sugestão)

```
# .env.example
JWT_SECRET=troque_por_um_valor_forte
JWT_EXPIRATION=86400000
SERVER_PORT=8080
```

`.gitignore` (sugestão mínima)

```
# arquivos de ambiente / dados locais
.env
.env.* 
data/
*.db
*.sqlite

# build / IDE
target/
.idea/
.vscode/
*.iml
```

Remoção de segredos já commitados (recomendações)

1. Apague o arquivo sensível do Git e mantenha localmente:
    - `git rm --cached .env`
    - `git commit -m "Remove .env do repositório"`
2. Troque/roteie a chave secreta caso tenha sido vazada.
3. Garanta que o workflow `.github/workflows/block-secrets.yaml` esteja ativo.

Testes
------
Executar testes:

```powershell
.\mvnw.cmd test
```

ou

```bash
./mvnw test
```

Documentação
------------

- Swagger UI: http://localhost:8080/api/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api/v3/api-docs

Contribuições e solicitações adicionais
---------------------------------------
Se desejar que os arquivos sugeridos (`.env.example`, `.gitignore`) ou uma coleção Postman sejam adicionados, abra uma
issue descrevendo a solicitação ou envie um pull request com as alterações propostas.

