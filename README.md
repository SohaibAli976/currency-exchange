# Currency Exchange Service

+----------------------+        +--------------------------+
|      BillController  |<>----->|    DiscountService       |
|----------------------|        +--------------------------+
| - discountService    |        | + calculateDiscount(...) |
| - currencyService    |        +--------------------------+
| + calculateBill(...) |                ^
+----------------------+                |
        |                               |
        v                               |
+----------------------+     +-----------------------------+
| CurrencyExchangeService|   |           Item              |
|-----------------------|    |-----------------------------|
| - restTemplate       |    | - name: String              |
| + getExchangeRate(...)|    | - price: BigDecimal         |
| + convertCurrency(...)|    | - category: String          |
+-----------------------+    +-----------------------------+
        ^
        |
        v
  +----------------+
  |  RestTemplate  |
  +----------------+

This Spring Boot application provides currency exchange and discount calculation services.

## How to Run

1. **Build the project:**
   mvn clean install
   mvn test
3. **Run the application:**

## Running Tests

To execute all unit tests:

## Generating Coverage Reports

To generate a code coverage report using JaCoCo:

The HTML report will be available at `target/site/jacoco/index.html`.

## Integrated Endpoint

This service integrates with an external currency exchange rates API.  
**Endpoint used:**  
`https://fake.api/{base_currency}` (replace with your actual API endpoint and key in production).

## Project Structure

- `src/main/java/com/example/currency_exchange/model` — Domain models (`Item`, `User`, etc.)
- `src/main/java/com/example/currency_exchange/service` — Business logic and services
- `src/test/java/com/example/currency_exchange/service` — Unit tests

## Notes

- Update your API URL and key in the application properties or as needed.
- The discount logic applies percentage discounts only to non-grocery items and a $5 discount for every $100 on the total bill.

- Here’s a sample Postman request for /api/calculate endpoint.  
Assuming JWT authentication, first obtain a token, then use it in the Authorization header.

**Step 1: Get JWT Token**

- **Method:** POST
- **URL:** http://localhost:8080/api/auth/login
- **Body (x-www-form-urlencoded):**
  - username`: testuser
  - password`: testpass

**Response:**
json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}

**Step 2: Calculate Bill**

- **Method:** POST
- **URL:** http://localhost:8080/api/calculate
- **Headers:**
  - Authorization: Bearer <token-from-step-1>
  - Content-Type: application/json
- **Body (raw, JSON):**
json
{
  "items": [
    { "name": "TV", "price": 300, "category": "electronics" },
    { "name": "Apple", "price": 100, "category": "groceries" }
  ],
  "user": {
    "name": "Alice",
    "userType": "EMPLOYEE",
    "registrationDate": "2021-06-01"
  },
  "originalCurrency": "USD",
  "targetCurrency": "EUR"
}


**Replace <token-from-step-1> with the actual token value.**

**Expected Response:**
json
{
  "originalAmount": 400,
  "discountedAmount": 325,
  "finalAmount": 298.75,
  "targetCurrency": "EUR"
}

*(Values will vary based on exchange rates and discounts.)*
