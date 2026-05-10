-- =========================================================
-- Script de Base de Datos - Prueba Técnica
-- =========================================================

-- ---------------------------------------------------------
-- ESQUEMA PARA DB_CLIENTES
-- ---------------------------------------------------------
-- CREATE DATABASE db_clientes; -- Ejecutado por Docker Compose

CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    edad INT,
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    clienteid VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN DEFAULT TRUE
);

-- Datos iniciales Clientes
INSERT INTO clientes (nombre, direccion, telefono, contrasena, estado, identificacion, genero, edad, clienteid)
VALUES ('Jose Lema', 'Otavalo sn y principal', '098254785', '1234', TRUE, '1234567890', 'Masculino', 30, 'C001')
ON CONFLICT DO NOTHING;

INSERT INTO clientes (nombre, direccion, telefono, contrasena, estado, identificacion, genero, edad, clienteid)
VALUES ('Marianela Montalvo', 'Amazonas y NNUU', '097548965', '5678', TRUE, '0987654321', 'Femenino', 28, 'C002')
ON CONFLICT DO NOTHING;

-- ---------------------------------------------------------
-- ESQUEMA PARA DB_CUENTAS
-- ---------------------------------------------------------
-- CREATE DATABASE db_cuentas; -- Ejecutado por Docker Compose

CREATE TABLE IF NOT EXISTS cuentas (
    id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(20) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    cliente_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS movimientos (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    cuenta_id BIGINT REFERENCES cuentas(id)
);

CREATE TABLE IF NOT EXISTS clientes_cache (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Datos iniciales Cuentas (Casos de uso)
INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES ('478758', 'Ahorro', 2000, TRUE, 1) ON CONFLICT DO NOTHING;

INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES ('225487', 'Corriente', 100, TRUE, 2) ON CONFLICT DO NOTHING;

-- Datos iniciales Cache (Simulación de sincronización RabbitMQ inicial)
INSERT INTO clientes_cache (id, nombre) VALUES (1, 'Jose Lema') ON CONFLICT DO NOTHING;
INSERT INTO clientes_cache (id, nombre) VALUES (2, 'Marianela Montalvo') ON CONFLICT DO NOTHING;
