# Food Ordering Application

## Overview
The Food Ordering Application is a Spring Boot-based RESTful API that allows users to create, update, retrieve, and delete food orders. The application is built using Java and Maven.

## Features
- Create a new order, food, table
- Update an existing order, food, table
- Retrieve an order, food, table by ID
- Retrieve all orders, foods, tables
- Delete an order, food, table

## Technologies Used
- Java
- Spring Boot
- MySQL
- Maven
- Lombok

## Getting Started
### Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- MySQL 8

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/oeraslan/foodorderingapplication.git
   ```
2. Install MySQL and Cretae a MySQL database named `food_ordering_application`
   ```sql
    CREATE DATABASE food_ordering_application
    ```
3. Update the `application.properties` file with your MySQL username and password.
4. Swagger UI can be accessed at `http://{your_api:port}/swagger-ui.html`. Generally it is `http://localhost:8080/swagger-ui.html`
