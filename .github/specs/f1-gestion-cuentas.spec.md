---
id: SPEC-002
status: APPROVED
feature: f1-gestion-cuentas
created: 2026-05-09
updated: 2026-05-09
author: spec-generator
version: "1.0"
related-specs: ["SPEC-001"]
---

# Spec: F1 - Gestión de Cuentas (CRUD)

> **Estado:** `DRAFT` → aprobar con `status: APPROVED` antes de iniciar implementación.

---

## 1. REQUERIMIENTOS

### Descripción
Implementar el mantenimiento de cuentas bancarias asociadas a clientes. Permite crear, editar, actualizar y eliminar cuentas.

### Requerimiento de Negocio
- **Cuenta**: número cuenta (PK), tipo cuenta, saldo inicial, estado.
- **Relación**: Una cuenta pertenece a un cliente (vía comunicación asincrónica o ID persistido).
- **Endpoint**: `/cuentas`

### Historias de Usuario

#### HU-02: Gestión de Cuentas
```
Como:        Administrador del sistema
Quiero:      Asociar cuentas bancarias a los clientes existentes
Para:        Permitir que los clientes realicen movimientos financieros

Prioridad:   Alta
Estimación:  S
Dependencias: SPEC-001 (Cliente debe existir)
Capa:        Backend (Microservicio 2)
```

#### Criterios de Aceptación — HU-02

**Happy Path**
```gherkin
CRITERIO-2.1: Creación de cuenta válida
  Dado que:  el cliente existe en el sistema
  Cuando:    invoco POST /cuentas con número "478758" y saldo "2000"
  Entonces:  se crea la cuenta y se asocia al cliente
```

### Reglas de Negocio
1. **Unicidad**: El número de cuenta debe ser único.
2. **Tipos de Cuenta**: Ahorro, Corriente.

---

## 2. DISEÑO

### Modelos de Datos

#### Entidades afectadas
| Entidad | Almacén | Cambios | Descripción |
|---------|---------|---------|-------------|
| `Cuenta` | tabla `cuentas` | nueva | Entidad principal de la cuenta |

#### Campos del modelo (Cuenta)
| Campo | Tipo | Obligatorio | Validación | Descripción |
|-------|------|-------------|------------|-------------|
| `numero_cuenta` | string | sí | PK | Número de cuenta único |
| `tipo_cuenta` | enum/string | sí | - | Ahorro / Corriente |
| `saldo_inicial` | decimal | sí | >= 0 | Saldo al momento de apertura |
| `estado` | boolean | sí | - | Estado de la cuenta |
| `cliente_id` | Long / UUID | sí | FK (conceptual) | Referencia al cliente |

### API Endpoints

#### POST /cuentas
- **Request Body**:
  ```json
  {
    "numeroCuenta": "478758",
    "tipoCuenta": "Ahorro",
    "saldoInicial": 2000,
    "estado": true,
    "clienteId": 1
  }
  ```

---

## 3. LISTA DE TAREAS

### Backend (Microservicio Cuenta/Movimientos)

#### Implementación
- [ ] Crear entidad `Cuenta`
- [ ] Implementar `CuentaRepository`
- [ ] Implementar `CuentaService`
- [ ] Implementar `CuentaController`
- [ ] Implementar validación de existencia de cliente (Síncrono vía Feign o asíncrono vía caché/mensajería)

#### Tests Backend
- [ ] `testCreateCuentaSuccess`: Test unitario de creación.
- [ ] `testCuentaInexistente`: Manejo de error al asociar a cliente inexistente.
