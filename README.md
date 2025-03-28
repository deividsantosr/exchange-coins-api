
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
cd exchange-coin-api
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

### `/api/exchange/exchange-info`
- **Method**: `POST`
- **Description**: Retrieve exchange information.
- **Request Body**: `ExchangeRequest`
- **Response**: Exchange information as a string.

### `/api/exchange/exchange`
- **Method**: `POST`
- **Description**: Perform a coin exchange.
- **Request Body**: `ExchangeRequest`
- **Response**: Exchange response as a `ExchangeResponse` object.

### `/api/exchange/reset`
- **Method**: `POST`
- **Description**: Reset the exchange machine.
- **Response**: Success message.

### `/api/exchange/update-coins`
- **Method**: `POST`
- **Description**: Update the quantity of a specific coin.
- **Request Parameters**:
    - `currencyValue`: The coin value.
    - `quantity`: The quantity of coins to update.
- **Response**: Success message.

## Swagger Documentation

Once the application is running, you can access the Swagger UI to explore and test the API at:

```
http://localhost:8080/swagger-ui.html
```

## License

This project is licensed under the MIT License.