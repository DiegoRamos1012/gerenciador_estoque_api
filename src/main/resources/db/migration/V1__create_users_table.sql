CREATE TABLE users
(
    -- Campos de User herdando colunas do BaseEntity
    id                TEXT PRIMARY KEY NOT NULL,
    name              TEXT             NOT NULL,
    email             TEXT             NOT NULL UNIQUE,
    password_hash     TEXT             NOT NULL,
    role              TEXT             NOT NULL,
    deleted           BOOLEAN          NOT NULL DEFAULT 0,
    deleted_at        DATETIME,
    created_at        DATETIME         NOT NULL,
    last_time_changed DATETIME
);

-- Índice opcional para acelerar buscas por e-mail, validado no domain
CREATE INDEX idx_users_email ON users (email);