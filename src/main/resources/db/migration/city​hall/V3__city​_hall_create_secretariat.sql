-- Criar schema
CREATE SCHEMA IF NOT EXISTS city_hall;

-- Criar tabela Secretariat
CREATE TABLE IF NOT EXISTS city_hall.secretariats (
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(60) NOT NULL,
    acronym  VARCHAR(15) NOT NULL
);

-- Criar tabela Servant
CREATE TABLE IF NOT EXISTS city_hall.servants (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(60),
    email           VARCHAR(255),
    birthday        DATE,
    secretariat_id  INT UNIQUE,
    CONSTRAINT fk_servant_secretariat
        FOREIGN KEY (secretariat_id)
        REFERENCES city_hall.secretariats(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- Índices opcionais
CREATE INDEX IF NOT EXISTS idx_servant_email
    ON city_hall.servants(email);

CREATE INDEX IF NOT EXISTS idx_secretariat_name
    ON city_hall.secretariats(name);
