# Banking System

A full-stack banking management system built with React, Spring Boot, Spring Security, JWT authentication, PostgreSQL, and Docker.

The application provides secure user authentication, bank account management, deposits, withdrawals, transfers, transaction history, profile management, password management, and password recovery functionality through a modern web interface.

---

## Table of Contents

* [Project Overview](#project-overview)
* [Features](#features)
* [Technology Stack](#technology-stack)
* [System Architecture](#system-architecture)
* [Project Structure](#project-structure)
* [Backend](#backend)
* [Frontend](#frontend)
* [Authentication and Security](#authentication-and-security)
* [Banking Operations](#banking-operations)
* [Password Management](#password-management)
* [Database](#database)
* [Docker](#docker)
* [Configuration](#configuration)
* [Clone / How to Run](#clone--how-to-run)
* [Running the Project Locally](#running-the-project-locally)
* [Running with Docker](#running-with-docker)
* [API Documentation](#api-documentation)
* [API Endpoints](#api-endpoints)
* [Testing](#testing)
* [Git and Version Control](#git-and-version-control)
* [Development Workflow](#development-workflow)
* [Project Design Principles](#project-design-principles)
* [Error Handling](#error-handling)
* [Security Considerations](#security-considerations)
* [Future Improvements](#future-improvements)
* [Project Status](#project-status)
* [Author](#author)
* [Repository](#repository)
* [License](#license)

---

# Project Overview

The Banking System is a full-stack web application designed to simulate core retail banking functionality.

The system consists of two primary applications:

1. A Spring Boot REST API responsible for authentication, authorization, account management, transactions, password management, and database communication.
2. A React frontend responsible for the user interface and communication with the backend API.

PostgreSQL is used as the relational database, while Docker Compose provides a containerized environment for the backend and database.

The project was developed with an emphasis on:

* Secure authentication
* RESTful API design
* Separation of frontend and backend responsibilities
* Database persistence
* Transaction management
* Password security
* Environment-based configuration
* Containerization
* Maintainable project structure

---

# Features

## Authentication

* User registration
* User login
* JWT-based authentication
* Secure password hashing
* Authentication state management
* Protected application routes
* Logout functionality

## Account Management

* Create a bank account
* Retrieve the authenticated user's account
* Display account number
* Display current balance
* Display account status
* Display account holder information

## Banking Transactions

* Deposit funds
* Withdraw funds
* Transfer funds
* Record transactions
* View transaction history
* Display transaction types
* Display transaction dates and amounts

Supported transaction types include:

* `DEPOSIT`
* `WITHDRAW`
* `TRANSFER_IN`
* `TRANSFER_OUT`

## User Profile

* View user profile
* Display personal account information
* Manage user-related settings

## Password Management

* Change password while authenticated
* Forgot-password workflow
* Password reset token generation
* Password reset
* Password reset token persistence
* Secure password handling

## Frontend Interface

* Responsive banking dashboard
* Navigation sidebar
* Account summary cards
* Balance overview
* Transaction summaries
* Recent transactions
* Transfer form
* Profile page
* Settings page
* Security settings
* Login page
* Registration page
* Forgot-password page
* Reset-password page
* Error pages

## Developer Features

* REST API
* Swagger/OpenAPI documentation
* PostgreSQL integration
* Docker Compose configuration
* Environment-based configuration
* Maven build system
* React/Vite development environment
* Git version control

---

# Technology Stack

## Backend

| Technology        | Purpose                          |
| ----------------- | -------------------------------- |
| Java 21           | Backend programming language     |
| Spring Boot       | Backend application framework    |
| Spring Web        | REST API                         |
| Spring Security   | Authentication and authorization |
| JWT               | Stateless authentication         |
| Spring Data JPA   | Database persistence             |
| Hibernate         | ORM                              |
| PostgreSQL        | Relational database              |
| Maven             | Dependency management and build  |
| Lombok            | Boilerplate reduction            |
| Springdoc OpenAPI | API documentation                |

## Frontend

| Technology   | Purpose               |
| ------------ | --------------------- |
| React        | User interface        |
| Vite         | Frontend build tool   |
| JavaScript   | Frontend programming  |
| HTML         | Application structure |
| CSS          | Application styling   |
| Axios        | HTTP communication    |
| React Router | Client-side routing   |
| Lucide React | Interface icons       |

## DevOps

| Technology     | Purpose                       |
| -------------- | ----------------------------- |
| Docker         | Containerization              |
| Docker Compose | Multi-container orchestration |
| Git            | Version control               |
| GitHub         | Source code hosting           |

---

# System Architecture

The application follows a layered full-stack architecture.

```text
                    Banking System
                          |
              +-----------+-----------+
              |                       |
              v                       v
       React Frontend          Spring Boot API
              |                       |
              |                       |
              +---------- HTTP --------+
                                      |
                              Spring Security
                                      |
                                  JWT Filter
                                      |
                               Service Layer
                                      |
                              Repository Layer
                                      |
                                  Hibernate
                                      |
                                  PostgreSQL
```

## Request Flow

A typical authenticated request follows this process:

```text
User
 |
 v
React Frontend
 |
 v
Axios HTTP Request
 |
 v
Spring Boot REST Controller
 |
 v
JWT Authentication Filter
 |
 v
Spring Security
 |
 v
Service Layer
 |
 v
Repository
 |
 v
PostgreSQL
 |
 v
Response
 |
 v
React Frontend
```

---

# Project Structure

```text
banking-system/
│
├── .gitignore
│
├── banking-api/
│   │
│   ├── pom.xml
│   ├── Dockerfile
│   ├── docker-compose.yml
│   │
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── banking_api/
│       │   │       │
│       │   │       ├── controller/
│       │   │       ├── dto/
│       │   │       ├── exception/
│       │   │       ├── model/
│       │   │       ├── repository/
│       │   │       ├── security/
│       │   │       └── service/
│       │   │           └── impl/
│       │   │
│       │   └── resources/
│       │       ├── application.properties
│       │       └── application-docker.properties
│       │
│       └── test/
│
└── banking-frontend/
    │
    ├── package.json
    ├── vite.config.js
    │
    └── src/
        ├── api/
        ├── app/
        ├── assets/
        ├── components/
        ├── constants/
        ├── context/
        ├── pages/
        ├── services/
        ├── utils/
        └── main.jsx
```

---

# Backend

The backend is implemented using Spring Boot and follows a layered architecture.

## Controller Layer

The controller layer exposes REST endpoints to the frontend.

Examples include:

* Authentication endpoints
* Account endpoints
* Transaction endpoints

The controllers receive HTTP requests, validate incoming data, and delegate business operations to the service layer.

## Service Layer

Business logic is implemented in service classes.

Examples include:

* `AuthService`
* `AuthServiceImpl`
* `PasswordResetService`
* `PasswordResetServiceImpl`
* `AccountService`
* `TransactionService`

The service layer prevents business logic from being tightly coupled to controllers or database repositories.

## Repository Layer

Spring Data JPA repositories provide database access.

Repositories are responsible for operations such as:

* Finding users
* Saving users
* Finding accounts
* Saving transactions
* Managing password reset tokens

## Model Layer

The backend contains domain entities representing the banking system.

Core entities include:

* `User`
* `Account`
* `Transaction`
* `PasswordResetToken`

---

# Frontend

The frontend is implemented using React and Vite.

The frontend communicates with the backend through REST API requests.

## Main Application Areas

### Authentication

```text
Login
Register
Forgot Password
Reset Password
```

### Dashboard

```text
Account Balance
Account Number
Account Status
Balance Overview
Transaction Summary
Quick Actions
Recent Transactions
```

### Banking

```text
Deposit
Withdraw
Transfer
Transactions
```

### User Management

```text
Profile
Settings
Security Settings
Change Password
```

---

# Authentication and Security

Security is an important part of the application architecture.

## JWT Authentication

The backend uses JSON Web Tokens for stateless authentication.

After successful login, the server generates a JWT.

The frontend stores the authentication information and sends the token with authenticated requests.

Requests use the following authorization format:

```text
Authorization: Bearer <JWT_TOKEN>
```

The backend JWT authentication filter extracts the token from the request and validates the authenticated user.

## Spring Security

Spring Security protects authenticated endpoints while allowing public authentication endpoints.

The security architecture uses:

* Stateless sessions
* JWT authentication
* Authentication filters
* Protected API endpoints
* Password hashing
* Role-based security infrastructure

## Password Security

Passwords are not stored as plaintext passwords.

The application uses Spring Security password encoding mechanisms to securely hash user passwords before persistence.

## Secret Management

Sensitive configuration is not intended to be committed directly to source control.

Configuration values such as:

```text
DB_PASSWORD
JWT_SECRET
JWT_EXPIRATION
```

are supplied through environment variables.

Local configuration files containing secrets are excluded from Git.

---

# Banking Operations

## Deposit

A deposit increases the user's account balance.

The operation:

1. Authenticates the user.
2. Retrieves the user's account.
3. Validates the requested amount.
4. Updates the account balance.
5. Creates a transaction record.

Transaction type:

```text
DEPOSIT
```

## Withdrawal

A withdrawal decreases the account balance.

The operation validates that sufficient funds are available before modifying the balance.

Transaction type:

```text
WITHDRAW
```

## Transfer

A transfer moves funds between accounts.

The system handles:

```text
Sender
   |
   | Transfer
   v
Recipient
```

Transfer transactions are recorded for both sides:

```text
TRANSFER_OUT
TRANSFER_IN
```

## Transaction History

Transactions are persisted in PostgreSQL and can be retrieved through the transaction API.

Each transaction contains information such as:

* Transaction ID
* Transaction type
* Amount
* Date
* Associated account

---

# Password Management

The system supports two password-management workflows.

## Change Password

An authenticated user can change their password through the Security Settings section.

The backend verifies the existing password before allowing the new password to be stored.

## Forgot Password

The password recovery workflow follows this general process:

```text
User enters email
        |
        v
Password reset request
        |
        v
Backend creates reset token
        |
        v
Reset token is persisted
        |
        v
User opens reset workflow
        |
        v
New password submitted
        |
        v
Password is securely updated
```

Password reset functionality is implemented using:

* `ForgotPasswordRequest`
* `ResetPasswordRequest`
* `PasswordResetToken`
* `PasswordResetTokenRepository`
* `PasswordResetService`
* `PasswordResetServiceImpl`

---

# Database

PostgreSQL is used as the primary relational database.

The application uses:

```text
Spring Data JPA
        |
        v
Hibernate
        |
        v
PostgreSQL
```

## Core Relationships

The main domain relationships are structured around users, accounts, and transactions.

```text
User
 |
 | 1-to-1
 v
Account
 |
 | 1-to-many
 v
Transactions
```

A password reset token is associated with the password recovery process.

## Database Configuration

The application supports environment-based database configuration.

Example:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/banking_system}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}
```

---

# Docker

Docker is used to provide a reproducible runtime environment for the application.

The Docker Compose configuration contains two primary services:

```text
PostgreSQL
    |
    v
Banking API
```

## PostgreSQL Container

The PostgreSQL service provides the banking database.

The database configuration is supplied through environment variables.

## Banking API Container

The Spring Boot application runs in its own container and connects to PostgreSQL using the Docker service name.

Inside Docker, the API connects to PostgreSQL using:

```text
jdbc:postgresql://postgres:5432/banking_system
```

The `postgres` hostname refers to the PostgreSQL Docker Compose service.

This differs from local development, where PostgreSQL is accessed through:

```text
localhost:5432
```

## Container Dependency

The banking API depends on PostgreSQL being healthy before starting.

The PostgreSQL health check uses:

```text
pg_isready
```

This prevents the backend from attempting to connect before the database is ready.

---

# Configuration

The main Spring Boot configuration uses environment variables for sensitive values.

Example:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/banking_system}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:86400000}
```

The configuration separates application code from environment-specific secrets.

Required environment variables include:

```text
DB_PASSWORD
JWT_SECRET
```

Optional variables include:

```text
DB_URL
DB_USERNAME
JWT_EXPIRATION
```

For Docker Compose, the environment variables are supplied to the containers.

---

# Clone / How to Run

## 1. Clone the Repository

```bash
git clone https://github.com/luvuyombewu-dev/Banking-System.git
cd Banking-System
```

## 2. Run the Backend Locally

Make sure PostgreSQL is running and create the `banking_system` database.

From Git Bash:

```bash
cd banking-api

export DB_URL=jdbc:postgresql://localhost:5432/banking_system
export DB_USERNAME=postgres
export DB_PASSWORD=your_database_password
export JWT_SECRET=your_jwt_secret
export JWT_EXPIRATION=86400000
```

Build the backend:

```bash
./mvnw clean package
```

Run the backend:

```bash
./mvnw spring-boot:run
```

The backend runs at:

```text
http://localhost:8080
```

## 3. Run the Frontend

Open a second terminal:

```bash
cd Banking-System/banking-frontend
npm install
npm run dev
```

The frontend normally runs at:

```text
http://localhost:5173
```

Open the application in your browser:

```text
http://localhost:5173
```

## 4. Run the Complete Application with Docker

From the backend directory:

```bash
cd Banking-System/banking-api
```

Set the required environment variables:

```bash
export DB_PASSWORD=your_database_password
export JWT_SECRET=your_jwt_secret
export JWT_EXPIRATION=86400000
```

Start PostgreSQL and the Spring Boot API:

```bash
docker compose up --build
```

Run in detached mode:

```bash
docker compose up --build -d
```

Check the containers:

```bash
docker compose ps
```

View backend logs:

```bash
docker compose logs banking-api
```

View PostgreSQL logs:

```bash
docker compose logs postgres
```

Stop the containers:

```bash
docker compose down
```

To remove the containers and PostgreSQL volume:

```bash
docker compose down -v
```

The API is available at:

```text
http://localhost:8080
```

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

The React frontend can then be started separately:

```bash
cd Banking-System/banking-frontend
npm install
npm run dev
```

The frontend normally runs at:

```text
http://localhost:5173
```

## Environment Variables

Do not commit real credentials or secrets to GitHub.

Use your own local values for:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
JWT_EXPIRATION
```

The repository excludes local configuration files containing secrets.

---

# Running the Project Locally

## Prerequisites

Install the following software:

* Java 21
* Maven
* Node.js
* npm
* PostgreSQL
* Git

Docker is optional for local development but recommended for reproducing the containerized environment.

---

# Running with Docker

Navigate to the backend directory:

```bash
cd banking-api
```

Set the required environment variables before starting Docker Compose.

Example:

```bash
export DB_PASSWORD=your_database_password
export JWT_SECRET=your_jwt_secret
export JWT_EXPIRATION=86400000
```

Start the containers:

```bash
docker compose up --build
```

The expected services are:

```text
banking-postgres
banking-api
```

The API is exposed on:

```text
http://localhost:8080
```

PostgreSQL is exposed on:

```text
localhost:5432
```

To run the containers in the background:

```bash
docker compose up --build -d
```

To view container status:

```bash
docker compose ps
```

To view API logs:

```bash
docker compose logs banking-api
```

To view PostgreSQL logs:

```bash
docker compose logs postgres
```

To stop the application:

```bash
docker compose down
```

To stop the containers and remove the database volume:

```bash
docker compose down -v
```

The `-v` option removes the PostgreSQL Docker volume and therefore removes persisted database data.

---

# API Documentation

The backend includes OpenAPI/Swagger documentation.

When the Spring Boot application is running, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger provides an interactive interface for exploring and testing the REST API.

Authenticated endpoints require the JWT bearer token.

---

# API Endpoints

The following table summarizes the main API functionality.

## Authentication

| Method | Endpoint                    | Description                          |
| ------ | --------------------------- | ------------------------------------ |
| POST   | `/api/auth/register`        | Register a new user                  |
| POST   | `/api/auth/login`           | Authenticate a user                  |
| PUT    | `/api/auth/change-password` | Change authenticated user's password |
| POST   | `/api/auth/forgot-password` | Start password recovery              |
| POST   | `/api/auth/reset-password`  | Reset password                       |

Authentication endpoints are handled by the authentication controller and service layer.

---

## Accounts

| Method | Endpoint                   | Description                           |
| ------ | -------------------------- | ------------------------------------- |
| POST   | `/api/accounts/create`     | Create an account                     |
| GET    | `/api/accounts/my-account` | Retrieve authenticated user's account |
| POST   | `/api/accounts/deposit`    | Deposit funds                         |
| POST   | `/api/accounts/withdraw`   | Withdraw funds                        |
| POST   | `/api/accounts/transfer`   | Transfer funds                        |

Most account endpoints require authentication.

---

## Transactions

| Method | Endpoint            | Description                  |
| ------ | ------------------- | ---------------------------- |
| GET    | `/api/transactions` | Retrieve transaction history |

Transaction access is protected by the application's authentication system.

---

# Example Authentication Flow

A typical login workflow looks like:

```text
POST /api/auth/login
        |
        v
Validate email/password
        |
        v
Authenticate user
        |
        v
Generate JWT
        |
        v
Return authentication response
        |
        v
Frontend stores authentication state
        |
        v
Frontend sends JWT with protected requests
```

Example authorization header:

```text
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

# Testing

The backend includes automated testing for service-level functionality.

Tests are implemented using the Spring testing ecosystem and Mockito where appropriate.

Run the Maven test suite with:

```bash
mvn test
```

A complete development verification should include:

```text
Application startup
        |
        v
Database connection
        |
        v
Registration
        |
        v
Login
        |
        v
JWT authentication
        |
        v
Account operations
        |
        v
Transaction recording
        |
        v
Password management
        |
        v
Frontend integration
```

---

# Git and Version Control

The project uses Git for source control and GitHub for remote repository hosting.

Repository:

```text
https://github.com/luvuyombewu-dev/Banking-System
```

The project includes `.gitignore` rules for files that should not be committed.

Examples include:

```text
target/
node_modules/
dist/
.env
IDE configuration files
local application configuration
compiled Java files
```

Sensitive configuration should never be committed to source control.

---

# Development Workflow

A recommended workflow for future development is:

```text
1. Create or switch to a feature branch
2. Implement the change
3. Run backend tests
4. Run frontend build
5. Test the affected feature
6. Check Git changes
7. Review sensitive files
8. Commit the changes
9. Push the branch
10. Merge after verification
```

Useful Git commands:

```bash
git status
```

```bash
git diff
```

```bash
git diff --cached
```

```bash
git add -A
```

```bash
git commit -m "Description of change"
```

```bash
git push origin main
```

Before committing, sensitive configuration should be checked.

For example:

```bash
git grep -n "password"
```

and:

```bash
git grep -n "JWT_SECRET"
```

Secrets should not appear in committed source files.

---

# Project Design Principles

The project follows several software engineering principles.

## Separation of Concerns

Frontend presentation, backend business logic, database access, and security responsibilities are separated.

## Layered Architecture

The backend separates:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

## Stateless Authentication

JWT authentication allows the API to remain stateless with respect to authentication sessions.

## Environment-Based Configuration

Sensitive and environment-specific configuration is provided through environment variables instead of hardcoding credentials into application code.

## Reusable Frontend Components

The React frontend uses reusable components for common interface elements and application functionality.

## RESTful Communication

The frontend communicates with the backend through HTTP-based REST endpoints.

---

# Error Handling

The backend contains centralized exception handling to provide consistent API error responses.

This allows errors to be handled without placing repetitive exception-handling logic in every controller.

The frontend also provides application-level error states for situations such as:

* Invalid credentials
* Failed requests
* Server errors
* Missing resources
* Authentication failures

---

# Security Considerations

The project implements several security mechanisms:

* JWT authentication
* Spring Security
* Password hashing
* Stateless sessions
* Protected API endpoints
* Environment-based secrets
* Local configuration exclusion
* Authentication filtering
* Token-based authorization

For a production banking application, additional security controls would be required, including:

* HTTPS/TLS
* Secure cookie/session strategies where applicable
* Key rotation
* Secret management infrastructure
* Rate limiting
* Account lockout policies
* Multi-factor authentication
* Audit logging
* Fraud detection
* Input validation hardening
* Database encryption
* Security monitoring
* Dependency vulnerability scanning

This project should therefore be considered an educational and portfolio banking application rather than a production financial institution platform.

---

# Future Improvements

Potential future development includes:

## Security

* Multi-factor authentication
* Email-based password reset delivery
* Refresh tokens
* JWT key rotation
* Rate limiting
* Account lockout
* Security audit logging

## Banking Features

* Beneficiary management
* Scheduled transfers
* Recurring payments
* Payment history filtering
* Account statements
* PDF statement generation
* Multiple accounts per user
* Transaction categories

## Administration

* Administrative dashboard
* User management
* Account management
* Transaction monitoring
* System audit logs
* Role-based administrative permissions

## Testing

* Controller tests
* Integration tests
* Repository tests
* End-to-end frontend tests
* Automated API testing
* Testcontainers-based PostgreSQL testing

## DevOps

* CI/CD pipeline
* Automated GitHub Actions builds
* Docker image publishing
* Production deployment
* Environment-specific deployments
* Monitoring and logging

## Frontend

* Improved accessibility
* Advanced transaction filtering
* Pagination improvements
* Mobile-first optimization
* Improved loading states
* Enhanced dashboard analytics

---

# Project Status

The core Banking System application has been implemented and pushed to GitHub.

Current implementation includes:

```text
Backend
    Spring Boot
    Spring Security
    JWT
    PostgreSQL
    JPA/Hibernate
    REST API
    Swagger/OpenAPI
    Password management
    Docker configuration

Frontend
    React
    Vite
    React Router
    Axios
    Banking dashboard
    Authentication
    Account management
    Transactions
    Transfers
    Profile
    Settings
    Password management

Infrastructure
    Docker
    Docker Compose
    PostgreSQL container
    Environment-based configuration

Version Control
    Git
    GitHub
```

The repository represents the completed core implementation of the banking application, with additional production-grade capabilities identified as future improvements.

---

# Author

Luvuyo Mbewu

Computer Engineering graduate and software developer focused on backend development, full-stack application development, systems engineering, and secure software architecture.

---

# Repository

GitHub:

https://github.com/luvuyombewu-dev/Banking-System

---

# License

This project is intended primarily for educational, portfolio, and demonstration purposes.

If a specific open-source license is required for redistribution or commercial use, a license such as MIT should be added to the repository explicitly.



# Screenshot
<img width="1312" height="604" alt="Banking_Login" src="https://github.com/user-attachments/assets/bc0c6f72-3014-4748-a17c-da6a6eebc306" />

<img width="1095" height="560" alt="Banking_SignUp_CreateAccount" src="https://github.com/user-attachments/assets/55f54aee-65f6-4bbb-99ef-f6f18491d6ee" />

<img width="1231" height="567" alt="Banking_ForgotPassword" src="https://github.com/user-attachments/assets/cf7e890a-4a92-48e0-adf6-ba205fbce3ce" />

<img width="1179" height="547" alt="Banking_ResetPassword" src="https://github.com/user-attachments/assets/531b3e79-dc9b-43ab-960e-2987634497e6" />

<img width="1336" height="671" alt="Banking_Dashboard" src="https://github.com/user-attachments/assets/7cef37c0-8380-483f-a31f-0bc908ef4f83" />

<img width="1337" height="666" alt="Banking_Profile" src="https://github.com/user-attachments/assets/4d8649cb-c3ac-49be-8fa5-e81962410a21" />

<img width="1343" height="669" alt="Banking_Transactions" src="https://github.com/user-attachments/assets/3867f4fd-1d40-4315-872c-723d314c6e96" />

<img width="1338" height="672" alt="Banking_RecentTransactions" src="https://github.com/user-attachments/assets/e31df915-3b50-4eb4-ab92-bc20b78809b5" />

<img width="1337" height="667" alt="Banking_Settings" src="https://github.com/user-attachments/assets/5def7a5e-d155-4cca-b4c1-88cd04aa4a01" />

