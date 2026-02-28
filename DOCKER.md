# Docker - Gerenciador de Estoque API

## Pré-requisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado e rodando

---

## Primeira vez (setup)

Construir a imagem e criar o container:

```bash
docker build -t gerenciador-estoque .
docker run -d --name gerenciador-estoque -p 8080:8080 -v C:\Users\Windows\IdeaProjects\gerenciador_estoque_api\data:/app/data gerenciador-estoque
```

> O `-v` mapeia a pasta `data/` do projeto para `/app/data` do container.
> Isso permite visualizar o `database.db` (SQLite) localmente em softwares como DBeaver.

---

## Uso diário

Ligar:

```bash
docker start gerenciador-estoque
```

Desligar:

```bash
docker stop gerenciador-estoque
```

---

## Reconstruir (após alterações no código)

```bash
docker rm -f gerenciador-estoque
docker build --no-cache -t gerenciador-estoque .
docker run -d --name gerenciador-estoque -p 8080:8080 -v C:\Users\Windows\IdeaProjects\gerenciador_estoque_api\data:/app/data gerenciador-estoque
```

## Ver logs

```bash
docker logs gerenciador-estoque
```

## Acessar a API

- **Base:** http://localhost:8080/api
- **Swagger:** http://localhost:8080/api/swagger-ui.html


