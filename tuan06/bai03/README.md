# Service-Based Architecture Demo (Spring Boot 3 + React)

## Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- SQL Server (manage by HeidiSQL)
- Lombok
- React (Vite)

## Project Structure

- `service-based-backend`: Backend API with 3 logical services
    - `user` package
    - `order` package
    - `payment` package
- `service-based-frontend`: React UI for testing APIs

## Database Tables

Tables are auto-created by JPA (`ddl-auto=update`):

- `users (id, name, email)`
- `orders (id, user_id, amount, status)`
- `payments (id, order_id, status)`

## Backend APIs

User Service:

- `POST /users`
- `GET /users/{id}`

Order Service:

- `POST /orders`
- `GET /orders/{id}`

Payment Service:

- `POST /payments`

## Business Flow

1. `POST /orders` creates an order with status `CREATED`.
2. Publish async event `OrderCreated` (simulated by Spring event + async listener).
3. Order Service calls Payment Service synchronously via REST (`RestTemplate`).
4. Payment Service creates payment and publishes `PaymentCompleted` event.
5. Order status is updated:
    - `PAID` when payment status is `SUCCESS`
    - `PAYMENT_FAILED` otherwise

## Environment Variables (No hardcoded credentials)

Copy `.env.example` values and set in your environment.

Backend env vars:

- `SERVER_PORT`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `PAYMENT_SERVICE_BASE_URL`

Frontend env vars:

- `VITE_API_BASE_URL`

## Run Backend

```bash
cd service-based-backend
mvn spring-boot:run
```

## Run Frontend

```bash
cd service-based-frontend
npm install
npm run dev
```

Frontend default URL: `http://localhost:5173`
Backend default URL: `http://localhost:8080`

## Sample Requests

Create user:

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"An","email":"an@example.com"}'
```

Create order (triggers payment):

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"amount":500}'
```

Get order:

```bash
curl http://localhost:8080/orders/1
```
