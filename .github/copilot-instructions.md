# Copilot Instructions

## ASDD Workflow (Agent Spec Software Development)

Este repositorio sigue el flujo **ASDD**: toda funcionalidad nueva se ejecuta en fases orquestadas por agentes especializados.

```
[Orchestrator] → [Spec Generator] → [Backend ∥ Frontend ∥ DB] → [Tests BE ∥ Tests FE] → [QA]
```

### Fases del flujo ASDD
1. **Spec**: El agente `spec-generator` genera la spec en `.github/specs/<feature>.spec.md`.
2. **Implementación (paralelo)**: `backend-developer` (Java/Spring Boot) + `frontend-developer` (React/Vite).
3. **Tests (paralelo)**: `test-engineer-backend` (JUnit 5 / Serenity) + `test-engineer-frontend`.
4. **QA**: `qa-agent` genera estrategia, Gherkin, E2E (Serenity) y valida APIs con **Postman v9.13.2**.

### Skills disponibles (slash commands):
- `/asdd-orchestrate` — orquesta el flujo completo ASDD
- `/generate-spec` — genera spec técnica en `.github/specs/`
- `/implement-backend` — implementa feature completo en el backend (Java/Spring)
- `/implement-frontend` — implementa feature completo en el frontend
- `/unit-testing` — genera suite de tests (JUnit/Mockito + Vitest)

---

## Mapa de Archivos e Instrucciones

### Agentes e Instrucciones (path-scoped)
| Scope | Instrucción | Se aplica a |
|---|---|---|
| **Backend** | `.github/instructions/backend.instructions.md` | `backend/src/main/java/**/*.java` |
| **Frontend** | `.github/instructions/frontend.instructions.md` | `frontend/src/**/*.{js,jsx}` |
| **Tests** | `.github/instructions/tests.instructions.md` | `backend/src/test/java/**/*.java` |

### Lineamientos y Contexto
| Documento | Ruta |
|---|---|
| Lineamientos de Desarrollo | `.github/docs/lineamientos/dev-guidelines.md` |
| Lineamientos QA | `.github/docs/lineamientos/qa-guidelines.md` |

---

## Reglas de Oro

> Principio rector: cada acción debe ser relevante, transparente y alineada con las instrucciones explícitas.

1. **Java 21 + Spring Boot 3**: Usar Records, inyección por constructor y Virtual Threads si aplica.
2. **PostgreSQL**: Las entidades JPA deben estar correctamente mapeadas.
3. **No implementation without a spec**: Consultar siempre `.github/specs/` antes de codificar.
4. **Clean Code**: Seguir principios SOLID y evitar código redundante.

---

## Diccionario de Dominio (Java/Spring Context)

| Término | Definición | Contexto Técnico |
|---------|-----------|------------------|
| **Entity** | Representación de tabla en DB | Clase con `@Entity` |
| **DTO / Record** | Objeto de transferencia de datos | Java Record |
| **Service** | Lógica de negocio orquestada | Clase con `@Service` |
| **Controller** | Punto de entrada REST | Clase con `@RestController` |
| **Repository** | Acceso a datos PostgreSQL | Interface con `JpaRepository` |
| **Endpoint** | Ruta expuesta por el Backend | `@GetMapping`, `@PostMapping` |

---

## Project Overview

> Ver `README.md` en la raíz del proyecto para detalles de infraestructura (Docker, Docker Compose).
