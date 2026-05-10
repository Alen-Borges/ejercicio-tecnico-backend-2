---
id: SPEC-001
status: APPROVED
feature: f1-gestion-clientes
created: 2026-05-09
updated: 2026-05-09
author: spec-generator
version: "1.0"
related-specs: []
---

# Spec: F1 - Gestión de Clientes (CRUD)

> **Estado:** `DRAFT` → aprobar con `status: APPROVED` antes de iniciar implementación.

---

## 1. REQUERIMIENTOS

### Descripción
Implementar el mantenimiento de clientes. Los clientes heredan de la clase Persona y deben permitir operaciones CRUD completas (Crear, Leer, Actualizar, Eliminar).

### Requerimiento de Negocio
- **Persona**: nombre, genero, edad, identificación, dirección, teléfono.
- **Cliente**: clienteid (PK), contraseña, estado. Hereda de Persona.
- **Endpoint**: `/clientes`

### Historias de Usuario

#### HU-01: Gestión de Clientes
```
Como:        Administrador del sistema
Quiero:      Gestionar la información de los clientes (CRUD)
Para:        Mantener actualizada la base de datos de usuarios del banco

Prioridad:   Alta
Estimación:  M
Dependencias: Ninguna
Capa:        Backend (Microservicio 1)
```

#### Criterios de Aceptación — HU-01

**Happy Path**
```gherkin
CRITERIO-1.1: Creación exitosa de cliente
  Dado que:  envío un JSON con los datos válidos de Persona y Cliente
  Cuando:    invoco POST /clientes
  Entonces:  se crea el registro en PostgreSQL y retorna 201 Created con el objeto
```

**Error Path**
```gherkin
CRITERIO-1.2: Cliente con identificación duplicada
  Dado que:  ya existe un cliente con identificación "12345"
  Cuando:    intento crear otro cliente con la misma identificación
  Entonces:  retorna 409 Conflict con un mensaje descriptivo
```

### Reglas de Negocio
1. **Herencia**: Cliente hereda de Persona. En base de datos se puede implementar como tabla única (Single Table) o tablas separadas (Joined).
2. **Identificación**: Debe ser única por cliente.
3. **Estado**: Por defecto debe ser `true` (Activo).

---

## 2. DISEÑO

### Modelos de Datos

#### Entidades afectadas
| Entidad | Almacén | Cambios | Descripción |
|---------|---------|---------|-------------|
| `Persona` | tabla `personas` | nueva | Clase base con datos demográficos |
| `Cliente` | tabla `clientes` | nueva | Clase que hereda de Persona con datos de cuenta |

#### Campos del modelo (Cliente)
| Campo | Tipo | Obligatorio | Validación | Descripción |
|-------|------|-------------|------------|-------------|
| `clienteid` | Long / UUID | sí | PK | ID único del cliente |
| `nombre` | string | sí | - | Nombre completo |
| `genero` | string | sí | - | Género de la persona |
| `edad` | int | sí | > 0 | Edad de la persona |
| `identificacion`| string | sí | única | DNI/Cédula (PK de Persona) |
| `direccion` | string | sí | - | Dirección domiciliaria |
| `telefono` | string | sí | - | Número de contacto |
| `contrasena` | string | sí | - | Password de acceso |
| `estado` | boolean | sí | - | Estado del cliente |

### API Endpoints

#### POST /clientes
- **Descripción**: Crea un nuevo cliente
- **Request Body**:
  ```json
  {
    "nombre": "Jose Lema",
    "genero": "Masculino",
    "edad": 30,
    "identificacion": "12345",
    "direccion": "Otavalo sn",
    "telefono": "098254785",
    "contrasena": "1234",
    "estado": true
  }
  ```
- **Response 201**: Objeto creado con su ID.

#### GET /clientes
- **Descripción**: Lista todos los clientes activos.

---

## 3. LISTA DE TAREAS

### Backend (Microservicio Cliente)

#### Implementación
- [ ] Crear entidad `Persona` (Abstracta o MappedSuperclass)
- [ ] Crear entidad `Cliente` extendiendo `Persona`
- [ ] Implementar `ClienteRepository` (Spring Data JPA)
- [ ] Implementar `ClienteService` con lógica de negocio
- [ ] Implementar `ClienteController` con endpoints CRUD
- [ ] Configurar excepciones globales (@RestControllerAdvice)

#### Tests Backend (F5)
- [ ] `testClienteDomain`: Implementar 1 prueba unitaria para la entidad de dominio Cliente.
- [ ] `testCreateCliente`: Prueba de integración (F6) para el endpoint POST /clientes.

### QA
- [ ] Validar contrato API con Postman v9.13.2
- [ ] Verificar persistencia en PostgreSQL
