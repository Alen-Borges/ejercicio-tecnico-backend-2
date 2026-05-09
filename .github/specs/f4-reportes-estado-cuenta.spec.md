---
id: SPEC-004
status: DRAFT
feature: f4-reportes-estado-cuenta
created: 2026-05-09
updated: 2026-05-09
author: spec-generator
version: "1.0"
related-specs: ["SPEC-001", "SPEC-003"]
---

# Spec: F4 - Reportes de Estado de Cuenta

> **Estado:** `DRAFT` → aprobar con `status: APPROVED` antes de iniciar implementación.

---

## 1. REQUERIMIENTOS

### Descripción
Generar un reporte detallado del estado de cuenta de un cliente en un rango de fechas específico. El reporte debe consolidar información de cuentas, saldos iniciales, movimientos y saldos disponibles.

### Requerimiento de Negocio
- **F4**: Reporte por rango de fechas y cliente.
- **Formato**: JSON.
- **Campos**: Fecha, Cliente, Número Cuenta, Tipo, Saldo Inicial, Estado, Movimiento, Saldo Disponible.

### Historias de Usuario

#### HU-04: Generación de Reportes
```
Como:        Cliente / Administrador
Quiero:      Ver el detalle de mis movimientos en un periodo de tiempo
Para:        Auditar mis transacciones y saldo actual

Prioridad:   Alta
Estimación:  M
Dependencias: SPEC-001, SPEC-003
Capa:        Backend (Orquestación / Microservicio 2)
```

#### Criterios de Aceptación — HU-04

**Happy Path**
```gherkin
CRITERIO-4.1: Reporte generado correctamente
  Dado que:  existen movimientos para el cliente "Jose Lema" entre "2022-02-01" y "2022-02-28"
  Cuando:    invoco GET /reportes?fecha=2022-02-01,2022-02-28&cliente=1
  Entonces:  retorna un JSON con la lista de movimientos y saldos detallada
```

### Reglas de Negocio
1. **Rango de Fechas**: Debe ser obligatorio.
2. **Consolidación**: El reporte debe cruzar datos de Clientes (Service 1) y Cuentas/Movimientos (Service 2). Dado que es Semi-Senior, se puede resolver mediante:
   - Comunicación síncrona (Feign Client) para obtener el nombre del cliente.
   - O bien, el reporte reside en el Microservicio 2 y asume que tiene el nombre del cliente cacheado o enviado por parámetro.

---

## 2. DISEÑO

### Modelos de Datos

#### DTO de Respuesta (ReporteDTO)
| Campo | Descripción |
|-------|-------------|
| `Fecha` | Fecha del movimiento |
| `Cliente` | Nombre del cliente |
| `Numero Cuenta` | Número de cuenta |
| `Tipo` | Tipo de cuenta (Ahorro/Corriente) |
| `Saldo Inicial` | Saldo antes del movimiento |
| `Estado` | Estado de la cuenta |
| `Movimiento` | Valor del movimiento |
| `Saldo Disponible` | Saldo después del movimiento |

### API Endpoints

#### GET /reportes
- **Parámetros**:
  - `fecha`: String (rango: "YYYY-MM-DD,YYYY-MM-DD")
  - `cliente`: ID del cliente
- **Response 200**:
  ```json
  [
    {
      "Fecha": "10/02/2022",
      "Cliente": "Marianela Montalvo",
      "Numero Cuenta": "225487",
      "Tipo": "Corriente",
      "Saldo Inicial": 100,
      "Estado": true,
      "Movimiento": 600,
      "Saldo Disponible": 700
    }
  ]
  ```

---

## 3. LISTA DE TAREAS

### Backend (Microservicio Movimientos/Reportes)

#### Implementación
- [ ] Crear `ReporteDTO`
- [ ] Implementar lógica de consulta filtrada por fecha y cliente en `MovimientoService`
- [ ] Integrar con Microservicio Clientes para obtener el nombre del cliente (si no está disponible localmente)
- [ ] Implementar `ReporteController`

#### Tests Backend
- [ ] `testReportGeneration`: Verificar que el cálculo de saldos en el reporte sea correcto.
