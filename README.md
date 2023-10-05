# Finance Tracker
* [General info](#general-info)
* [Features](#features)
* [Technologies](#technologies)
* [Setup](#setup)
* [Authors](#authors)

## General info
Finance Tracker is backend application for budget managment. Depending on the assigned role (ADMIN or USER), application provides distinct privileges and varying scopes of possibilities. Users can access the service through API endpoints to perform operations on their incomes and expenses. Users receive scheduled, summarized financial reports via email, keeping them informed about their financial data.

## Features
The application is built upon a microservice architecture and consists of three services: api-service, report-service, and mail-service, which provide:
- **Registration & Login**: Users can register an account and log in. Additionally, they have the option to log in using GitHub. All authentication is based on JWT
- **Finance Management**: Users can effortlessly control their incomes and expenses through simple CRUD (Create, Read, Update, Delete) operations
- **Reports Generator**: Generates financial reports on a scheduled basis
- **Mail Notifications**: Automated email notifications to users, including greeting emails after registration and scheduled financial reports via email
  
## Technologies

### Backend
  - Spring Boot
  - Spring Web
  - Spring Security
  - Spring Data MongoDB
  - Spring AMPQ - RabbitMQ
  - Lombok
  - Mapstruct

### Backend Testing
  - Spock
  - JUnit
  - Mockito
  - Testcontainers

### Database
  - MongoDB

### Other
  - Docker
  - Swagger

## Setup
To run Finance Tracker, follow steps:
1. Clone project

  ``` bash      
   git clone https://github.com/gabriela-plis/finance-tracker.git
  ```

2. Open cloned directory
  ``` bash      
   cd finance-tracker
  ```

3. Build project

  ``` bash
  ./gradlew clean build
  ```

4. Go to docker directory

  ``` bash      
   cd docker
  ```

5. Run using docker-compose 

  ``` bash
  docker-compose up -d
  ```

## Authors
- [Gabriela Plis](https://github.com/gabriela-plis)
- [Shoshinmas](https://github.com/shoshinmas)
