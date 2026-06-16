# Travel Management System - REST API

A Spring Boot REST API application for managing travel trips with MySQL database backend.

## Features

- **REST API Endpoints** for CRUD operations on trips
- **MySQL Database** for persistent data storage
- **JSON Response Format** for all API endpoints
- **Input Validation** using Jakarta Validation
- **Global Exception Handling** for consistent error responses
- **Swagger UI Documentation** for API exploration
- **Lombok** for reducing boilerplate code
- **Transactional Operations** with proper logging

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose (for MySQL setup)
- MySQL 8.0+ (or use Docker Compose)

## Project Structure

```
my-travel-management/
├── src/
│   ├── main/
│   │   ├── java/com/travelmanagement/
│   │   │   ├── TravelManagementApplication.java    # Main entry point
│   │   │   ├── controller/
│   │   │   │   └── TripController.java              # REST endpoints
│   │   │   ├── service/
│   │   │   │   └── TripService.java                 # Business logic
│   │   │   ├── model/
│   │   │   │   └── Trip.java                        # JPA Entity
│   │   │   ├── repository/
│   │   │   │   └── TripRepository.java              # Database access
│   │   │   └── exception/
│   │   │       └── GlobalExceptionHandler.java      # Error handling
│   │   └── resources/
│   │       └── application.properties               # Configuration
│   └── test/
├── pom.xml                                          # Maven dependencies
├── docker-compose.yml                               # MySQL Docker setup
└── README.md                                        # This file
```

## Getting Started

### 1. Clone/Setup the Project

```bash
cd my-travel-management
```

### 2. Setup MySQL Database using Docker

```bash
docker-compose up -d
```

This will start a MySQL container with:
- Root user: `root` / Password: `root`
- Database: `travel_management_db`
- User: `traveler` / Password: `traveler123`

Or manually configure MySQL connection in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/travel_management_db
spring.datasource.username=root
spring.datasource.password=root
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or run directly with:
```bash
java -jar target/my-travel-management-1.0.0.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

### Health Check
- **GET** `/api/trips/health/status` - Check API status

### Trip Management
- **GET** `/api/trips` - Get all trips
- **GET** `/api/trips/{id}` - Get trip by ID
- **GET** `/api/trips/traveler/{travelerName}` - Get trips by traveler name
- **GET** `/api/trips/status/{status}` - Get trips by status
- **POST** `/api/trips` - Create new trip
- **PUT** `/api/trips/{id}` - Update trip
- **DELETE** `/api/trips/{id}` - Delete trip

### API Documentation
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## Example Request/Response

### Create a Trip
```bash
curl -X POST http://localhost:8080/api/trips \
  -H "Content-Type: application/json" \
  -d '{
    "destination": "Paris",
    "description": "Spring vacation in Paris",
    "startDate": "2026-07-01",
    "endDate": "2026-07-15",
    "travelerName": "John Doe",
    "budget": 5000,
    "status": "PLANNED"
  }'
```

### Response (JSON)
```json
{
  "id": 1,
  "destination": "Paris",
  "description": "Spring vacation in Paris",
  "startDate": "2026-07-01",
  "endDate": "2026-07-15",
  "travelerName": "John Doe",
  "budget": 5000,
  "status": "PLANNED",
  "createdAt": "2026-06-16"
}
```

## Database Schema

### Trips Table
```sql
CREATE TABLE trips (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  destination VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  traveler_name VARCHAR(255) NOT NULL,
  budget DOUBLE NOT NULL,
  status VARCHAR(50) DEFAULT 'PLANNED',
  created_at DATE DEFAULT CURRENT_DATE
);
```

## Dependencies

- **Spring Boot 3.4.5** - Web framework
- **Spring Data JPA** - ORM and database access
- **MySQL Connector Java** - JDBC driver
- **Lombok** - Boilerplate reduction
- **Spring Validation** - Input validation
- **SpringDoc OpenAPI** - Swagger UI integration

## Configuration

Key properties in `application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/travel_management_db
spring.datasource.username=root
spring.datasource.password=root

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Logging
logging.level.com.travelmanagement=DEBUG
```

## Running Tests

```bash
mvn test
```

## Stopping Docker Container

```bash
docker-compose down
```

To also remove volumes:
```bash
docker-compose down -v
```

## Common Issues

### Cannot connect to MySQL
- Ensure Docker container is running: `docker-compose ps`
- Check MySQL connection credentials in `application.properties`
- Wait for container to be fully initialized (check health)

### Tables not created automatically
- Set `spring.jpa.hibernate.ddl-auto=create` for first run
- Then change to `update` for subsequent runs

### Port 8080 already in use
- Change port in `application.properties`: `server.port=8081`

## Future Enhancements

- Add user authentication and authorization (Spring Security)
- Implement pagination and sorting
- Add trip itinerary/activities management
- Implement expense tracking
- Add budget alerts
- Integrate with mapping APIs
- Add file upload for trip documents
- Implement caching with Redis
- Add comprehensive unit and integration tests

## License

This project is open source and available under the MIT License.

## Support

For issues or questions, please open an issue in the repository.
