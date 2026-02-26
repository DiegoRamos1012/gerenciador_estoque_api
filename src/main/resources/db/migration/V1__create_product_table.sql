CREATE TABLE product
(
    id                TEXT PRIMARY KEY NOT NULL,
    name              TEXT             NOT NULL,
    -- O SQlite irá criar um index automático pra coluna abaixo
    product_code      TEXT UNIQUE      NOT NULL,
    price             NUMERIC          NOT NULL,
    quantity          INTEGER          NOT NULL,
    description       TEXT             NOT NULL,
    status            TEXT             NOT NULL,
    deleted           BOOLEAN          NOT NULL DEFAULT 0,
    deleted_at        DATETIME,
    created_at        DATETIME         NOT NULL,
    last_time_changed DATETIME
);

