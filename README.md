# IgorBlazhko Hotel Booking System

Demonstration backend project for a lab defense. The domain is intentionally simplified to hotel booking: hotels, rooms, bookings, payments, users, roles, and uploaded files.

## Stack

- Java 23
- Spring Boot 3.4.5
- Gradle
- PostgreSQL
- Spring Security + JWT
- Swagger UI
- Docker + Docker Compose

## Implemented Requirements

- Layered architecture: controller, service, repository
- RESTful endpoints with GET, POST, PUT, DELETE
- Path parameters and query parameters
- PostgreSQL integration
- DTO classes and MapStruct mappings
- Validation and global exception handling
- Pagination, sorting, search, and filtering on room search endpoint
- Registration creates a USER, login issues JWT, ADMIN manages hotels and rooms, USER books rooms
- File upload and download
- 3 asynchronous processes with `@Async` and `CompletableFuture`
- Swagger UI documentation
- Logging for requests, errors, and important actions
- Dockerfile, docker-compose, multistage build, healthcheck, logging options, runtime optimization

## Main Entities

- IgorBlazhkoUserEntity
- IgorBlazhkoRoleEntity
- IgorBlazhkoPropertyEntity (hotel)
- IgorBlazhkoRoomEntity
- IgorBlazhkoBookingEntity
- IgorBlazhkoPaymentEntity
- IgorBlazhkoStoredFileEntity

## Default Admin

- Email: `admin@igorblazhko.com`
- Password: `Admin12345`

## Run Locally

```bash
gradlew.bat bootRun
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Run With Docker

```bash
docker compose up --build
```

## Main Endpoints

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/users`
- `GET /api/properties`
- `POST /api/properties`
- `GET /api/rooms?search=double&city=almaty&available=true&page=0&size=5&sortBy=pricePerNight&sortDir=asc`
- `POST /api/rooms`
- `GET /api/bookings?clientId=1`
- `POST /api/bookings`
- `PUT /api/bookings/{id}/status?status=CONFIRMED`
- `POST /api/payments`
- `PUT /api/payments/{id}`
- `POST /api/files/upload`
- `GET /api/files/{id}/download`
- `GET /api/reports/bookings/summary`

## Suggested Commit Sequence

1. Initialize Gradle Spring Boot project with Java 23
2. Add application configuration and Docker infrastructure
3. Create booking domain entities and enums
4. Add repositories for all entities
5. Add DTOs for auth, users, properties, rooms, bookings, payments, files, reports
6. Add MapStruct mappers
7. Add custom exceptions and global exception handler
8. Add room search specification with pagination/filtering/sorting
9. Add JWT utility and user principal classes
10. Add Spring Security configuration and JWT filter
11. Implement auth service and controller
12. Implement user management service and controller
13. Implement property management service and controller
14. Implement room management service and controller
15. Implement booking service and controller
16. Implement payment service and controller
17. Implement file storage service and controller
18. Add async booking/report service
19. Add report controller and async endpoint
20. Add request logging filter and OpenAPI config
21. Add data initializer with roles and admin user
22. Add integration smoke test with H2
23. Finalize README and endpoint examples
24. Final Docker and configuration cleanup
