# POI (Point of Interest) Service

The POI Service is a Spring Boot microservice responsible for managing Points of Interest and their associated tags for the travel application. It provides a robust REST API for creating, retrieving, updating, and categorizing geographical locations and attractions.

## Features

* **POI Management:** Full CRUD operations (Create, Read, Update, Patch, Delete) for Points of Interest.
* **Tagging System:** Create and associate reusable tags with POIs for flexible categorization.
* **Advanced Filtering:** Retrieve POIs filtered by category or specific tags.
* **Complex Data Support:** Natively handles unstructured `opening_hours` data using PostgreSQL's `JSONB` format.
* **Caching:** Method-level caching enabled to optimize frequent read operations.

## Tech Stack

* **Java** (17+)
* **Spring Boot** (Web, Data JPA, Cache)
* **Hibernate** (Used for `JSONB` custom type mapping)
* **PostgreSQL** (Required for `JSONB` and `UUID` column types)
* **Lombok** (Boilerplate reduction)
* **Jakarta Validation** (Request validation)

## Domain Models

### POI (Point of Interest)
* `id`: UUID (Primary Key)
* `name`: String
* `description`: String
* `category`: String
* `latitude`: Double (-90.0 to 90.0)
* `longitude`: Double (-180.0 to 180.0)
* `openingHours`: JSON Object (Mapped to `jsonb` in DB)
* `tags`: Set of Tags (Many-to-Many)

### Tag
* `id`: Long (Primary Key)
* `name`: String (Unique)

## API Reference

### POI Endpoints (`/api/v1/pois`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/api/v1/pois` | Create a new POI. |
| **GET** | `/api/v1/pois/{id}` | Retrieve a specific POI by its UUID. |
| **GET** | `/api/v1/pois` | Retrieve all POIs. Optionally filter by category (`?category=...`). |
| **PUT** | `/api/v1/pois/{id}` | Fully update an existing POI. |
| **PATCH** | `/api/v1/pois/{id}` | Partially update an existing POI. |
| **POST** | `/api/v1/pois/{poiId}/tags` | Add a tag to a specific POI (`?tagName=...`). |
| **GET** | `/api/v1/pois/by-tag/{tagName}` | Retrieve a list of POIs associated with a specific tag. |

### Tag Endpoints (`/api/v1/tags`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/api/v1/tags` | Create a new global tag. |
| **GET** | `/api/v1/tags` | Retrieve all available tags. |

## Configuration Notes

### Database Requirements
Because the `Poi` entity maps the `openingHours` attribute to a `jsonb` column using `@JdbcTypeCode(SqlTypes.JSON)`, **PostgreSQL is required** to run this service. In-memory databases like H2 may not fully support this specific JSON mapping without custom dialects.

### Caching
Caching is enabled at the configuration level (`@EnableCaching` in `CacheConfig.java`). Ensure you have a configured cache manager (e.g., Redis, Caffeine, or ConcurrentHashMap) depending on your application properties.

## Getting Started

1. Ensure a PostgreSQL instance is running and configured in your `application.yml` or `application.properties`.
2. Build the project using Maven or Gradle.
   ```bash
   ./mvnw clean install
   ```
3. Run the application.
    ```bash
    ./mvnw spring-boot:run
    ```