
# Exchange Coin API

This is a simple API for exchanging bills to coins, built using Spring Boot. The API allows users to perform various operations like getting exchange information, performing exchanges, resetting the exchange machine, and updating the quantity of coins.

## Features

- **Exchange Info**: Retrieve exchange information based on a provided request.
- **Perform Exchange**: Execute a coin exchange based on the provided details.
- **Reset Machine**: Reset the exchange machine to its initial state.
- **Update Coins**: Update the quantity of a specific coin in the machine.

## Requirements

- Java 17 or higher
- Spring Boot 3.0.0+
- Maven 3.6.0+

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/deividsantosr/exchange-coins-api.git
cd exchange-coins-api
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The application will start and be accessible at `http://localhost:8080`.

## Endpoints

The following endpoints are available in the API:

# `/api/exchange/exchange-info`
- **Method**: `POST`
- **Description**: Retrieve exchange information.
- **Request Body**: `ExchangeRequest`
- **Response**: Exchange information as a string.

**Request Body:**
```json
{
  "value": 0,
  "maximizeCoins": true
}
```

**Response Body:**

```text
Exchanged $30 successfully!

Statement:
- 100 coins (25 cents) = $25.0
- 50 coins (10 cents) = $5.0

Bills amount: $30
Coins amount: $11.0
- 50 coins (10 cents) = $5.0)
- 100 coins (5 cents) = $5.0)
- 100 coins (1 cent) = $1.0)
  Total amount: $41.0
```

# `/api/exchange/exchange`
- **Method**: `POST`
- **Description**: Perform a coin exchange.
- **Request Body**: `ExchangeRequest`
- **Response**: Exchange response as a `ExchangeResponse` object.

**Request Body:**
```json
{
  "value": 0,
  "maximizeCoins": true
}
```

**Response Body:**
```json
{
  "success": true,
  "message": "Exchanged $0 successfully!",
  "summary": "",
  "usedCoins": [],
  "currentCoins": [
    {
      "currencyValue": 25,
      "quantity": 100
    },
    {
      "currencyValue": 10,
      "quantity": 100
    },
    {
      "currencyValue": 5,
      "quantity": 100
    },
    {
      "currencyValue": 1,
      "quantity": 100
    }
  ],
  "currentNotes": [],
  "totalCoins": 41.0,
  "totalNotes": 0,
  "total": 41.0,
  "state": "Bills amount: $0\nCoins amount: $41.0\n- 100 coins (25 cents) = $25.0)\n- 100 coins (10 cents) = $10.0)\n- 100 coins (5 cents) = $5.0)\n- 100 coins (1 cent) = $1.0)\nTotal amount: $41.0"
}
```

# `/api/exchange/reset`
- **Method**: `PATCH`
- **Description**: Reset the exchange machine.
- **Response**: Success message.

**Response Body:**

```text
Machine reset successfully!
```

# `/api/exchange/update-coins`
- **Method**: `PATCH`
- **Description**: Update the quantity of a specific coin.
- **Request Parameters**:
    - `currencyValue`: The coin value.
    - `quantity`: The quantity of coins to update.
- **Response**: Success message.

**Request Body:**
```text
?currencyValue=25&quantity=100
```

**Response Body:**

```text
Coin updated successfully!
```

## Swagger Documentation

Once the application is running, you can access the Swagger UI to explore and test the API at:

```
http://localhost:8080/swagger-ui.html
```

## License

This project is licensed under the MIT License.