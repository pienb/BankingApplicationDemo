## Description
This application provides an Account and Customer Management system built with Spring Boot. It offers RESTful APIs for managing accounts and customers. The application allows an account creation for a customer. In addition, allowd the customer to visualize his/her personal and account details.

## Installation Requirements

Java 17 or higher
Maven 3.8.1 or higher

Steps to set up repo
* Clone the repository, Github link: https://github.com/pienb/BankingApplicationDemo/
* Build the project: mvn clean install
* Start the application: mvn spring-boot:run
* The application will now be running at http://localhost:8080.

## API Endpoints
### Account
POST /api/accounts: Create an account in the accounts table
Request body:
```
{
    "customer_id": 1,
    "iban": "NL08ABNA123456789"
}
```

POST /api/accounts/{id}/deposit: Add a transaction in the transactions table to deposit money
Request body:

```
{
    "amount": 1000
}
```

POST /api/accounts/{id}/withdraw: Add a transaction in the transactions table to withdraw money
Request body:

```
{
    "amount": 1000
}

```

GET /api/accounts/{id}: Get the requested account by id
Response body:

```
{
    "id": 5,
    "customer": {
        "customer_id": 2,
        "name": "Joop",
        "address": {
            "addressId": 11,
            "street": "hoofdstraat",
            "zip_code": "2071DC",
            "house_number": "6"
        },
    "age": 34
    },
    "iban": "NL08ABNA123456789",
    "balance": 1010.0
}
```

## Customer
GET /api/customers/{id}: Get the requested customer by id
Response body:

```
{
    "customer_id": 2,
    "name": "Joop",
    "address": {
        "addressId": 11,
        "street": "hoofdstraat",
        "zip_code": "2071DC",
        "house_number": "6"
    },
    "age": 34
}
```

POST /api/customers: Create a customer in the customers table
Request body:

```
{
    "name": "Joop",
    "address": {
        "street": "hoofdstraat",
        "zip_code": "2071DC",
        "house_number": "8"
        },
    "date_of_birth": "01-01-1990"
}
```
