-- Script de creación de Base de Datos para Microservicio de Clientes
-- Basado en requerimientos de la prueba técnica

-- Creación de tabla única para manejar la herencia Persona/Cliente (Single Table strategy)
-- Alternativamente se pueden usar tablas separadas, pero esta es eficiente para este caso.

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

-- Índices recomendados para búsquedas frecuentes
CREATE INDEX IF NOT EXISTS idx_cliente_identificacion ON clientes(identificacion);
CREATE INDEX IF NOT EXISTS idx_cliente_clienteid ON clientes(clienteid);

-- Datos de prueba iniciales (Casos de uso Ejemplos)
INSERT INTO clientes (nombre, direccion, telefono, contrasena, estado, identificacion, genero, edad, clienteid)
VALUES ('Jose Lema', 'Otavalo sn y principal', '098254785', '1234', TRUE, '1234567890', 'Masculino', 30, 'C001')
ON CONFLICT DO NOTHING;

INSERT INTO clientes (nombre, direccion, telefono, contrasena, estado, identificacion, genero, edad, clienteid)
VALUES ('Marianela Montalvo', 'Amazonas y NNUU', '097548965', '5678', TRUE, '0987654321', 'Femenino', 28, 'C002')
ON CONFLICT DO NOTHING;

INSERT INTO clientes (nombre, direccion, telefono, contrasena, estado, identificacion, genero, edad, clienteid)
VALUES ('Juan Osorio', '13 junio y Equinoccial', '098874587', '1245', TRUE, '1122334455', 'Masculino', 35, 'C003')
ON CONFLICT DO NOTHING;
