# Vending Machine API Documentation

# Introduction
This API provides endpoints for managing a a simple sales management system, which should include product management, client management, sales operations management, reporting, and user authentication features.

# Technologies Used

- Java Spring Boot (version 3.2.2)
- Spring Data JPA
- Hibernate
- MySQL

# Running Procedure
- Clone the project.
- Configure your database settings in application.properties.
- Run MachineApplciation.java in vending-machine\src\main\java\com\vendor\machine\MachineApplication.java.
- Test the API with Swagger: [Swagger UI](http://localhost:8080/swagger-ui/index.html#/)

# Test Users
- to test the endpoints you should register, and login using user endpoint and use the token to authenticate.

**Note**: When a token is not sent with the request, the following error appears: 
- can't parse JSON. Raw result: {"error": "Error in Accessing the resource: Cannot invoke "String.substring(int)" because "authHeader" is null"}

# Request/Response Format
All requests and responses are in JSON format.

# Entity Models Documentation

## Client

Represents a client in the system.

### Attributes

- **id**: Long - Unique identifier for the client.
- **name**: String - Name of the client.
- **lastName**: String - Last name of the client.
- **mobile**: String - Mobile number of the client.
- **email**: String - Email address of the client.
- **address**: String - Address of the client.

## Product

Represents a product in the system.

### Attributes

- **id**: Long - Unique identifier for the product.
- **productName**: String - Name of the product.
- **description**: String - Description of the product.
- **category**: String - Category of the product.
- **amountAvailable**: Long - Available quantity of the product.
- **creationDate**: Date - Date when the product was created.
- **cost**: Long - Cost of the product.

## Sale

Represents a sale transaction in the system.

### Attributes

- **id**: Long - Unique identifier for the sale.
- **creationDate**: Date - Date when the sale was created.
- **clientId**: Long - Identifier of the client associated with the sale.
- **sellerId**: Long - Identifier of the seller associated with the sale.
- **productId**: Long - Identifier of the product associated with the sale.
- **total**: BigDecimal - Total cost of the sale.
- **transactions**: List<Transaction> - List of transactions associated with the sale.

## Transaction

Represents a transaction record associated with a sale.

### Attributes

- **id**: Long - Unique identifier for the transaction.
- **sale**: Sale - Reference to the sale associated with the transaction.
- **quantity**: BigDecimal - Quantity of the product involved in the transaction.
- **cost**: BigDecimal - Cost of the product involved in the transaction.

## User

Represents a user in the system.

### Attributes

- **id**: Long - Unique identifier for the user.
- **username**: String - Username of the user.
- **password**: String - Password of the user.


# Endpoints Documentation

## Sale Controller

### GET /api/v1/sale
Get all sales.

### PUT /api/v1/sale
Update data of sale.

### POST /api/v1/sale
Add sale to list of sales.

### GET /api/v1/sale/{id}
Get sale by id.

### DELETE /api/v1/sale/{id}
Delete the sale.

## Product Controller

### GET /api/v1/product
Get all products.

### PUT /api/v1/product
Update data of product.

### POST /api/v1/product
Add product to list of products.

### GET /api/v1/product/{id}
Get product by id.

### DELETE /api/v1/product/{id}
Delete the product.

## Client Controller

### GET /api/v1/client
Get all clients.

### PUT /api/v1/client
Update data of client.

### POST /api/v1/client
Add client to list of clients.

### GET /api/v1/client/{id}
Get client by id.

### DELETE /api/v1/client/{id}
Delete the client.

## User Controller

### POST /api/v1/users/signin
Log in user.

### POST /api/v1/users/register
Register user.

### GET /api/v1/users
Get user details of logged-in user.

### DELETE /api/v1/users
Delete the logged-in user.

## Reports Controller

### GET /api/v1/reports/sale/top-selling
Get top selling products by date range.

### GET /api/v1/reports/sale/top-performer
Get top performing products by date range.

### GET /api/v1/reports/sale/revenue
Get total revenue by date range.

### GET /api/v1/reports/sale/all
Get sale by date range.

### GET /api/v1/reports/product/performance
Get products performance.

### GET /api/v1/reports/product/low-inventory
Get products low inventory.

### GET /api/v1/reports/product/count
Get total number of products.

### GET /api/v1/reports/product/analysis
Get pricing analysis.

### GET /api/v1/reports/client/top-spending
Get top spending clients.

### GET /api/v1/reports/client/location
Get client locations.

### GET /api/v1/reports/client/count
Get total number of clients.

### GET /api/v1/reports/client/activity
Get client activity.

# Upcoming improvements
- Handling Logging and Auditing
