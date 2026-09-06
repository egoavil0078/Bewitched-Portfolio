# Bewitched Marketplace

**Full-stack e-commerce marketplace developed with Spring Boot, Kotlin, MySQL, Docker and AWS.**

Bewitched is a complete marketplace application composed of a native Android client and a REST API backend.

The project includes user authentication, product management, shopping cart, order processing, payments, administration, database migrations, cloud storage and deployment on AWS.

> The complete source code is kept in a private repository.  
> This public repository is intended as a technical portfolio and project showcase.

---

## Overview

Bewitched was designed as a complete marketplace platform with two main components:

- **Android application** developed with Kotlin
- **Backend REST API** developed with Java and Spring Boot

The application communicates with external services such as Stripe and AWS and uses MySQL for persistent data storage.

---

## Main Features

### User features

- User registration and login
- JWT authentication
- Email confirmation
- Product catalog
- Product categories
- Product detail pages
- Shopping cart
- Order management
- Stripe payments
- User profile management
- Password recovery

### Administration

- ADMIN / USER roles
- Product management
- User management
- Order management
- Administrative endpoints
- Account blocking and security controls

---

## Technology Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT
- REST APIs
- Maven
- Flyway

### Android

- Kotlin
- Android SDK
- Retrofit
- Glide
- Material Design

### Database

- MySQL
- Amazon RDS
- Flyway migrations

### Payments

- Stripe API
- Stripe Checkout
- Stripe Webhooks

### Cloud & DevOps

- Docker
- Amazon ECR
- Amazon ECS
- AWS Fargate
- Amazon RDS
- Amazon S3
- Application Load Balancer
- AWS Certificate Manager
- AWS Secrets Manager
- Amazon VPC
- Security Groups

### Development Tools

- Git
- GitHub
- IntelliJ IDEA
- Android Studio
- Visual Studio Code
- Postman

---

## Architecture

```text
                    INTERNET
                        │
                        ▼
               HTTPS / Custom Domain
                        │
                        ▼
          Application Load Balancer
                        │
                        ▼
                 Amazon ECS
                  AWS Fargate
                        │
                        ▼
               Spring Boot API
                  │          │
                  │          └──────────► Amazon S3
                  │
                  ▼
               Amazon RDS
                  MySQL
                  
Android Application
        │
        └──────── REST API ─────────────► Backend

Backend
   │
   └────────────────────────────────────► Stripe
```

The backend is packaged as a Docker image and stored in **Amazon ECR**.

Amazon ECS with **AWS Fargate** runs the application without requiring direct management of EC2 servers.

The database is hosted in **Amazon RDS** and protected inside the application's VPC.

---

## AWS Network Architecture

The infrastructure uses public and private subnets distributed across multiple Availability Zones.

```text
                    Internet
                       │
                Internet Gateway
                       │
          ┌────────────┴────────────┐
          │                         │
    Public Subnet 1           Public Subnet 2
          │                         │
          └────────── ALB ──────────┘
                       │
          ┌────────────┴────────────┐
          │                         │
   Private Subnet 1          Private Subnet 2
        ECS Task                  ECS Task
          │                         │
          └────────────┬────────────┘
                       │
                       ▼
                  Amazon RDS
```

Security Groups restrict communication between each layer of the application.

For example:

```text
Internet → ALB       : HTTPS / 443
ALB → ECS            : Backend port
ECS → RDS            : MySQL / 3306
```

---

## Security

The project implements several security mechanisms:

- Spring Security
- JWT authentication
- Role-based authorization
- Protected ADMIN endpoints
- Login attempt limits
- Temporary account blocking
- Environment-based secret management
- AWS Secrets Manager
- HTTPS certificates with AWS Certificate Manager
- Security Groups
- Private database networking

Sensitive credentials are never stored directly in the source code.

Example:

```properties
spring.datasource.password=${DB_PASSWORD}
app.jwt.secret=${JWT_SECRET}
stripe.secret.key=${STRIPE_SECRET_KEY}
```

---

## Database Versioning

Database migrations are managed with **Flyway**.

Each database modification is stored as a versioned SQL migration.

Example:

```text
V1__initial_schema.sql
V2__add_orders.sql
V3__update_users.sql
V4__update_payments.sql
```

This allows the database schema and application code to evolve together.

---

## Screenshots

### Login

![Bewitched Login](docs/screenshots/login.png)

### Marketplace

![Bewitched Home](docs/screenshots/pantalla_principal.png)

### Product Catalog

![Bewitched Product Catalog](docs/screenshots/catalogo.png)

### Product Detail

![Bewitched Product Detail](docs/screenshots/ficha_producto.png)

### Shopping Cart

![Bewitched Shopping Cart](docs/screenshots/carrito.png)

### User Profile

![Bewitched User Profile](docs/screenshots/perfil.png)

### Administration

![Bewitched Administration](docs/screenshots/perfil_admin.png)

---

## Project Structure

The private development repository is organized into two main applications:

```text
MarketplaceBewitched/
│
├── android/
│   └── Native Android application
│
└── backend/
    └── Spring Boot REST API
```

---

## Development Workflow

A typical backend deployment follows this process:

```text
Development
    │
    ▼
Git
    │
    ▼
Docker Build
    │
    ▼
Amazon ECR
    │
    ▼
Amazon ECS / Fargate
    │
    ▼
Spring Boot starts
    │
    ▼
Flyway migrations
    │
    ▼
Application available
```

---

## Source Code

The complete source code is maintained in a **private repository**.

Selected code examples may be included in this portfolio repository to demonstrate architecture, backend development, Android development and security practices without publishing the complete commercial application.

---

## Project Status

**Active development**

Bewitched started as a full-stack marketplace project and has progressively evolved to include:

- Cloud deployment
- Docker
- AWS infrastructure
- Database versioning with Flyway
- Payment processing
- Security improvements
- Production-oriented configuration

---

## Author

**Kevin Egoavil Barrionuevo**

Junior Java Backend / Android Developer

**Main technologies:** Java · Spring Boot · Kotlin · Android · SQL · Docker · AWS