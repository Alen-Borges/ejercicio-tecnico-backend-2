---
id: SPEC-003
status: APPROVED
feature: f2-f3-registro-movimientos
created: 2026-05-09
updated: 2026-05-09
author: spec-generator
version: "1.0"
related-specs: ["SPEC-002"]
---

# Spec: F2, F3 - Registro de Movimientos y Validación de Saldo

> **Estado:** `DRAFT` → aprobar con `status: APPROVED` antes de iniciar implementación.

---

## 1. REQUERIMIENTOS

### Descripción
Implementar el registro de movimientos (débitos y créditos) en las cuentas bancarias. Cada movimiento debe actualizar el saldo disponible de la cuenta y validar que existan fondos suficientes en caso de retiros.

### Requerimiento de Negocio
- **F2**: Registro de movimientos positivos (depósitos) o negativos (retiros). Actualización de saldo.
- **F3**: Si no hay saldo suficiente para un retiro, lanzar error "Saldo no disponible".
- **Movimiento**: Fecha, tipo movimiento, valor, saldo (disponible después del movimiento).

### Historias de Usuario

#### HU-03: Registro de Movimientos
```
Como:        Cliente del banco
Quiero:      Realizar depósitos y retiros en mi cuenta
Para:        Gestionar mi dinero de forma efectiva

Prioridad:   Crítica
Estimación:  L
Dependencias: SPEC-002 (Cuenta debe existir)
Capa:        Backend (Microservicio 2)
```

#### Criterios de Aceptación — HU-03

**Happy Path (Depósito)**
```gherkin
CRITERIO-3.1: Depósito exitoso
  Dado que:  la cuenta "478758" tiene un saldo de 2000
  Cuando:    realizo un depósito de 600
  Entonces:  el saldo de la cuenta se actualiza a 2600 y se registra el movimiento
```

**Happy Path (Retiro)**
```gherkin
CRITERIO-3.2: Retiro exitoso
  Dado que:  la cuenta "478758" tiene un saldo de 2000
  Cuando:    realizo un retiro de 575
  Entonces:  el saldo de la cuenta se actualiza a 1425 y se registra el movimiento
```

**Error Path (Saldo Insuficiente)**
```gherkin
CRITERIO-3.3: Retiro con saldo insuficiente
  Dado que:  la cuenta "495878" tiene un saldo de 0
  Cuando:    intento retirar 100
  Entonces:  el sistema no registra el movimiento y retorna error 400 con el mensaje "Saldo no disponible"
```

### Reglas de Negocio
1. **Idempotencia**: Un movimiento no debe registrarse si la actualización de saldo falla.
2. **Tipo de Movimiento**: Debe derivarse del valor (Positivo = Depósito, Negativo = Retiro).
3. **Saldo en Movimiento**: El campo `saldo` en la entidad Movimiento debe reflejar el saldo *después* de aplicada la operación.

---

## 2. DISEÑO

### Modelos de Datos

#### Entidades afectadas
| Entidad | Almacén | Cambios | Descripción |
|---------|---------|---------|-------------|
| `Movimiento` | tabla `movimientos` | nueva | Registro histórico de transacciones |
| `Cuenta` | tabla `cuentas` | modificada | Se actualiza el campo saldo |

#### Campos del modelo (Movimiento)
| Campo | Tipo | Obligatorio | Validación | Descripción |
|-------|------|-------------|------------|-------------|
| `id` | Long / UUID | sí | PK | ID único de transacción |
| `fecha` | datetime | sí | - | Fecha de ejecución |
| `tipo_movimiento`| string | sí | - | Depósito / Retiro |
| `valor` | decimal | sí | != 0 | Monto de la operación |
| `saldo` | decimal | sí | - | Saldo resultante después de la op |

### API Endpoints

#### POST /movimientos
- **Request Body**:
  ```json
  {
    "numeroCuenta": "478758",
    "valor": -575
  }
  ```
- **Lógica Interna**:
  1. Buscar cuenta.
  2. Calcular `nuevo_saldo = saldo_actual + valor`.
  3. Si `nuevo_saldo < 0`, lanzar `InsufficientBalanceException`.
  4. Actualizar `cuenta.saldo = nuevo_saldo`.
  5. Crear y guardar `Movimiento` con los datos calculados.

---

## 3. LISTA DE TAREAS

### Backend (Microservicio Movimientos)

#### Implementación
- [ ] Crear entidad `Movimiento`
- [ ] Implementar `MovimientoRepository`
- [ ] Implementar `MovimientoService` con `@Transactional`
- [ ] Definir `InsufficientBalanceException`
- [ ] Implementar `MovimientoController`

#### Tests Backend
- [ ] `testRegisterDepositSuccess`: Happy path depósito.
- [ ] `testRegisterWithdrawSuccess`: Happy path retiro.
- [ ] `testInsufficientBalance`: Verificar mensaje "Saldo no disponible".
