# Employee Management API

## Finalized stack

- Java 21 (LTS), Spring Boot 4.1.0, Spring Security 7, Spring Data JPA, Maven
- PostgreSQL 18.4, Spring Boot Flyway starter, JJWT 0.12.6
- OpenAPI/Swagger UI through springdoc-openapi 3.0.0
- Planned frontend: React, TypeScript, Vite, Material UI, TanStack Query, React Hook Form, Zod

## Roles

| Role | Permissions |
| --- | --- |
| `ADMIN` | Full employee/department management and deactivation |
| `HR_MANAGER` | Create, view, update employees; view departments |
| `EMPLOYEE` | Authenticated base role; self-service endpoints will be added next |

## Run locally

1. Install Java 21, Maven, and either PostgreSQL 18.4 or Docker Desktop.
2. With Docker: `docker compose up -d`.
3. Run: `mvn spring-boot:run`.
4. Swagger UI: `http://localhost:8080/api/swagger-ui.html`.
5. Log in through `POST /api/v1/auth/login` using `admin` / `Admin@123`, then use the token in Swagger's **Authorize** dialog.

Use `POST /api/v1/users` as an `ADMIN` to create `HR_MANAGER` and `EMPLOYEE` accounts. Do not expose this endpoint to untrusted users.

Change the default admin password and set `JWT_SECRET` before any non-local deployment.

The API uses UTC internally to avoid Windows legacy timezone identifiers (such as `Asia/Calcutta`) being rejected by PostgreSQL 18.
