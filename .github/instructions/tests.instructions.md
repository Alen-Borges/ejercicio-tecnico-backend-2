---
applyTo: "backend/src/test/java/**/*.java,frontend/src/__tests__/**/*.{js,jsx}"
---

> **Scope**: Las reglas de backend aplican a proyectos con tests en Java (JUnit 5); las de frontend aplican a proyectos con tests en JS/JSX. Seguir los principios de independencia, aislamiento y AAA.

# Instrucciones para Archivos de Pruebas (Unitarias y E2E)

## Principios

- **Independencia**: Cada test es 100% independiente.
- **Aislamiento**: Mockear dependencias externas (DB, APIs externas).
- **Cobertura**: Cubrir happy path, error path y edge cases. Cobertura ≥ 80%.

## Backend Unit Testing (JUnit 5 + Mockito)

### Estructura de archivos
```
backend/src/test/java/com/example/project/
  service/UserServiceTest.java
  controller/UserControllerTest.java
```

### Convenciones
- Usar `@ExtendWith(MockitoExtension.class)`.
- Usar `@Mock` para dependencias y `@InjectMocks` para el sistema bajo prueba.
- Patrón BDD en tests: `given()`, `when()`, `then()` (de Mockito BDDMockito).

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void createUser_ShouldReturnUser_WhenDataValid() {
        // GIVEN
        User user = new User("John");
        given(userRepository.save(any())).willReturn(user);
        // WHEN
        User result = userService.create(user);
        // THEN
        assertThat(result.getName()).isEqualTo("John");
    }
}
```

## Backend Integration/E2E Testing (Serenity BDD + RestAssured)

### Estructura
```
backend/src/test/java/com/example/project/e2e/
  steps/UserSteps.java
  features/UserScenarios.java
```

### Convenciones
- Usar `@SerenityTest`.
- Usar **RestAssured** para las peticiones HTTP en las pruebas de integración.
- Los tests deben ser legibles y descriptivos, siguiendo el patrón BDD.

```java
@SerenityTest
public class UserScenarios {
    @Steps
    UserSteps userSteps;

    @Test
    public void searchForUserByName() {
        userSteps.when_i_search_for_user_named("John");
        userSteps.then_i_should_see_user_details();
    }
}
```

## Backend API Validation (Postman v9.13.2)

### Estructura
```
docs/output/qa/postman/
  <feature>.postman_collection.json
  <feature>.postman_environment.json
```

### Convenciones
- Usar **Postman v9.13.2** para validar los contratos de la API.
- Las colecciones deben incluir tests de script (JavaScript) para verificar códigos de estado, esquemas JSON y tiempos de respuesta.
- Exportar colecciones y ambientes a la carpeta de salida de QA.

```javascript
// Ejemplo de test en Postman
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});
pm.test("Response is valid JSON", function () {
    pm.response.to.be.json;
});
```

## Frontend (Vitest + Testing Library)

Mantener el uso de **Vitest** y **React Testing Library**, pero asegurar la limpieza de mocks.

### Convenciones FE
- Usar `vi.mock()` para módulos externos.
- Limpiar con `beforeEach(() => vi.clearAllMocks())`.

## Nunca hacer

- Tests que dependan de una base de datos real (usar `@DataJpaTest` con H2 o Testcontainers si es integración).
- Lógica compleja en los tests.
- Omitir aserciones.

---

> Para quality gates y nomenclatura Gherkin avanzada, ver `.github/docs/lineamientos/qa-guidelines.md`.
