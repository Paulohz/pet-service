# Pet Service

Microserviço responsável pelo gerenciamento de pets disponíveis para adoção.

## Stack

- Java 21
- Spring Boot 4
- PostgreSQL 16
- Docker / Docker Compose

## Pré-requisitos

- [Java 21](https://sdkman.io/)
- [Docker](https://www.docker.com/)

## Como rodar

1. Copie o arquivo de exemplo e preencha as variáveis:

```bash
cp .env.example .env
```

2. Inicie a aplicação:

```bash
./gradlew bootRun
```

O Docker Compose é iniciado automaticamente junto com a aplicação. A aplicação estará disponível em `http://localhost:8080`.

## Variáveis de ambiente

| Variável          | Descrição              | Exemplo    |
|-------------------|------------------------|------------|
| `POSTGRES_USER`     | Usuário do banco       | `postgres` |
| `POSTGRES_PASSWORD` | Senha do banco         | `postgres` |
| `POSTGRES_DB`       | Nome do banco de dados | `petdb`    |
| `POSTGRES_PORT`     | Porta exposta          | `5432`     |

## Endpoints

| Método   | Rota                        | Descrição                              |
|----------|-----------------------------|----------------------------------------|
| `POST`   | `/v1/api/pets`              | Cadastrar um novo pet                  |
| `GET`    | `/v1/api/pets`              | Listar pets (filtro por status e cidade)|
| `GET`    | `/v1/api/pets/{id}`         | Buscar pet por ID                      |
| `PATCH`  | `/v1/api/pets/{id}/status`  | Atualizar status do pet                |
| `PUT`    | `/v1/api/pets/{id}/location`| Atualizar localização do pet           |
| `DELETE` | `/v1/api/pets/{id}`         | Remover pet (soft delete)              |

### Filtros disponíveis em `GET /v1/api/pets`

| Parâmetro | Tipo       | Obrigatório | Descrição            |
|-----------|------------|-------------|----------------------|
| `status`  | `AVAILABLE` \| `ADOPTED` | Não | Filtra por status |
| `city`    | `string`   | Não         | Filtra por cidade    |

## Documentação interativa

Com a aplicação rodando, acesse o Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

## Testes

Os testes utilizam banco H2 em memória e não dependem do Docker:

```bash
./gradlew test
```