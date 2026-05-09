# AGENTS.md — ASDD Project (Java/Spring Stack)

This file defines general guidance for all AI agents working in this repository, following the **ASDD (Agent Spec Software Development)** workflow.

## Project Summary (New Stack)

- **Backend**: Java 21 + Spring Boot 3.x
- **Frontend**: React 18 + Vite
- **Database**: PostgreSQL
- **Architecture**: Controller-Service-Repository
- **Testing**: JUnit 5 + Mockito + Serenity BDD + Postman v9.13.2
- **Infrastructure**: Docker + docker-compose (PostgreSQL)

## ASDD Workflow

**Every new feature must follow this pipeline:**

```
[FASE 1 — Secuencial]
spec-generator    → /generate-spec      → .github/specs/<feature>.spec.md

[FASE 2 — Paralelo ∥]
database-agent    → Entity mapping, DDL, Liquibase/Flyway
backend-developer → Spring layers (Controller/Service/Repo/DTO)
frontend-developer→ React components / hooks / api-services

[FASE 3 — Paralelo ∥]
test-engineer-backend  → backend/src/test/java/
test-engineer-frontend → frontend/src/__tests__/

[FASE 4 — Secuencial]
qa-agent          → Gherkin, E2E (Serenity), Postman v9.13.2 (API Validation)
```

## Critical Rules for All Agents

1. **Precision**: High fidelity to the technical spec in `.github/specs/`.
2. **Java Standards**: Use Records, inyección por constructor and avoid unnecessary boilerplate.
3. **Reactive/Async**: Use Virtual Threads (Java 21) where applicable for I/O operations.
4. **Relational Integrity**: Maintain proper FK and indices in PostgreSQL.
5. **No implementation without a spec.** Always check `.github/specs/` first.

---
> Last update: 2026-04-21 - Stack migration to Java/Spring Boot/PostgreSQL.
