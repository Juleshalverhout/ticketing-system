# 🎟️ Spring Boot Ticketing System API

A robust, production-ready RESTful API built with **Java 23** and **Spring Boot**. This application simulates a full-featured issue tracking and support ticketing backend, allowing users to create tickets, manage statuses, and assign tasks to specific support agents.

## 🚀 Key Features & Enterprise Patterns

This project was built adhering to industry-standard backend architecture and design patterns:

* **3-Tier Layered Architecture:** Strict separation of concerns using Controllers (Web Layer), Services (Business Logic Layer), and Repositories (Data Access Layer).
* **Data Transfer Objects (DTOs):** Utilizes modern **Java 23 Records** (`TicketResponseDTO`) to decouple database entities from the API response, preventing data over-exposure and infinite JSON recursion loops.
* **Relational Database Mapping (ORM):** Uses Spring Data JPA / Hibernate to map entities to a **PostgreSQL** database, featuring `@ManyToOne` foreign key relationships between Tickets and Users.
* **Strict Data Validation:** Protects the database using Jakarta Validation (`@Valid`, `@NotBlank`, `@Size`) to reject malformed payloads with automatic `400 Bad Request` responses.
* **Type Safety & Data Integrity:** Employs Java **Enums** (`TicketStatus`) mapped as strings in the database to prevent invalid status entries (e.g., typos in API queries).
* **Global Exception Handling:** Follows the "Fast Fail" principle using custom exceptions (`ResourceNotFoundException`) mapped to clean `404 Not Found` HTTP status codes.
* **Database Bootstrapping:** Implements a `CommandLineRunner` to automatically seed the PostgreSQL database with initial test data upon application startup.

---

## 🛠️ Tech Stack

* **Language:** Java 23
* **Framework:** Spring Boot 3.x (Spring Web, Spring Data JPA)
* **Database:** PostgreSQL 17
* **Build Tool:** Maven
* **Tools:** Postman, pgAdmin 4, Git/GitHub

---

## 📋 API Endpoints

### 🎟️ Tickets
| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/tickets` | Create a new ticket (Requires Title & Description) |
| `GET` | `/api/tickets` | Retrieve a list of all tickets (Returns clean DTOs) |
| `GET` | `/api/tickets/{id}` | Retrieve a specific ticket by its ID |
| `PUT` | `/api/tickets/{id}` | Update an existing ticket's details or status |
| `PUT` | `/api/tickets/{ticketId}/assign/{userId}` | Assign a specific ticket to a specific user |
| `GET` | `/api/tickets/search?status={STATUS}` | Filter tickets by status (`OPEN`, `IN_PROGRESS`, `CLOSED`) |

### 👤 Users
| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/users` | Create a new user (`ADMIN`, `SUPPORT`, `CUSTOMER`) |

---

## ⚙️ Local Setup & Running the Application

### Prerequisites
* JDK 23 installed
* PostgreSQL 17 installed and running on default port `5432`

### 1. Database Configuration
Create an empty database in PostgreSQL named `ticketing_db`.
Open `src/main/resources/application.properties` and update your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ticketing_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD
spring.jpa.hibernate.ddl-auto=update