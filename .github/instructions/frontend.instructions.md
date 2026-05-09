---
applyTo: "frontend/src/**/*.{js,jsx}"
---

> **Scope**: Se aplica a proyectos con capa frontend en React. Mantener arquitectura limpia y separación de responsabilidades.

# Instrucciones para Archivos de Frontend (React 18 / Vite)

## Convenciones Obligatorias

- **Componentes**: Usar componentes funcionales y Hooks.
- **CSS**: SIEMPRE usar CSS Modules (`*.module.css`) o la solución de estilos definida en el proyecto. NUNCA estilos en línea para lógica compleja.
- **Nombres**: PascalCase para componentes y páginas (`.jsx`), camelCase para hooks y servicios.
- **Auth state**: SIEMPRE consumir del Contexto de Autenticación global (`AuthContext`). El token JWT debe manejarse de forma centralizada.
- **Env vars**: SIEMPRE con prefijo `VITE_` para que Vite las exponga.

## Estructura de Archivos

```
src/
  api/axiosConfig.js      ← Configuración de Axios con interceptores para JWT
  context/AuthContext.jsx ← Fuente de verdad para el estado de auth
  services/               ← Llamadas a la API Spring Boot
  components/             ← Componentes reutilizables
  pages/                  ← Vistas completas
```

## Llamadas a la API Backend (Spring Boot)

Usar siempre **Axios**. Las llamadas van en `services/`. El backend espera DTOs en formato JSON.

```js
// services/userService.js
import api from '../api/axiosConfig';

export async function getUsers() {
  const res = await api.get('/api/users');
  return res.data;
}
```

## Rutas (React Router v6)

Las rutas se registran en `src/App.jsx` o un archivo de rutas dedicado. Usar componentes `ProtectedRoute` para proteger accesos.

## Nunca hacer

- Almacenar secretos en el código fuente.
- Realizar llamadas a la API directamente desde los componentes (usar servicios/hooks).
- Lógica de negocio pesada en la capa de presentación.

---

> Para estándares de UX/UI y accesibilidad, ver `.github/docs/lineamientos/dev-guidelines.md`.
