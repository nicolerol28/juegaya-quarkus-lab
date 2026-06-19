# juegaya-quarkus-lab

Proyecto de práctica para **Quarkus**. Implementa un CRUD de canchas deportivas con arquitectura en capas, replicando conceptos conocidos de Spring Boot en el ecosistema Quarkus.

---

## Stack tecnológico

| Tecnología | Versión | Rol |
|---|---|---|
| Java | 21 | Lenguaje |
| Quarkus | 3.36.3 | Framework |
| Hibernate ORM Panache | (via Quarkus BOM) | ORM + Repository pattern |
| PostgreSQL | última (Dev Services) | Base de datos |
| Bean Validation (Hibernate Validator) | (via Quarkus BOM) | Validación de requests |
| RESTEasy Reactive + Jackson | (via Quarkus BOM) | API REST + serialización JSON |

---

## Arquitectura en capas

```
CourtResource  (JAX-RS)
     │  recibe HTTP, delega, retorna CourtResponse
     ▼
CourtService   (@ApplicationScoped)
     │  lógica de negocio, @Transactional, lanza excepciones
     ▼
CourtRepository  (PanacheRepository<Court>)
     │  acceso a datos, métodos CRUD heredados de Panache
     ▼
Court          (@Entity)
               tabla en PostgreSQL
```

**DTOs involucrados:**
- `CourtRequest` — entrada del cliente (con validaciones Bean Validation)
- `CourtResponse` — salida hacia el cliente (sin exponer la entidad directamente)
- `CourtMapper` — convierte entre entidad y DTOs, sin lógica de negocio

**Manejo de errores:**
- `ValidationExceptionMapper` captura `ConstraintViolationException` y retorna `400 Bad Request` con los mensajes de validación.
- `CourtNotFoundException` (excepción de dominio) y su mapper retornan `404 Not Found` cuando una cancha no existe, manteniendo el Service desacoplado al protocolo HTTP.
---

## Cómo correr el proyecto

### Requisitos previos

- Java 21
- Maven (o usar el wrapper incluido `./mvnw`)
- **Docker** (requerido para Quarkus Dev Services — levanta PostgreSQL automáticamente)

### Modo desarrollo

```bash
./mvnw quarkus:dev
```

Quarkus Dev Services detecta que no hay una datasource configurada manualmente y levanta un contenedor PostgreSQL automáticamente. No se necesita ninguna variable de entorno ni configuración adicional para desarrollo local.

La API queda disponible en `http://localhost:8080`.

---

## Endpoints disponibles

| Método | Ruta | Descripción | Response |
|---|---|---|---|
| `GET` | `/courts` | Lista todas las canchas | `200 OK` |
| `GET` | `/courts/{id}` | Obtiene una cancha por ID | `200 OK` / `404 Not Found` |
| `POST` | `/courts` | Crea una nueva cancha | `201 Created` |
| `PUT` | `/courts/{id}` | Actualiza una cancha existente | `200 OK` / `404 Not Found` |
| `DELETE` | `/courts/{id}` | Elimina una cancha | `204 No Content` / `404 Not Found` |

---

## Ejemplos de request/response

### Crear una cancha — `POST /courts`

**Request:**
```json
{
  "name": "Cancha Norte",
  "sport": "futbol5",
  "available": true
}
```

**Response `201 Created`:**
```json
{
  "id": 1,
  "name": "Cancha Norte",
  "sport": "futbol5",
  "available": true
}
```

**Deportes válidos:** `futbol5`, `tenis`, `padel`

### Listar todas las canchas — `GET /courts`

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "name": "Cancha Norte",
    "sport": "futbol5",
    "available": true
  },
  {
    "id": 2,
    "name": "Cancha Sur",
    "sport": "tenis",
    "available": false
  }
]
```

### Error de validación — `POST /courts` con datos inválidos

**Request:**
```json
{
  "name": "",
  "sport": "rugby"
}
```

**Response `400 Bad Request`:**
```json
{
  "error": "El nombre es obligatorio, Deporte no permitido"
}
```

---
