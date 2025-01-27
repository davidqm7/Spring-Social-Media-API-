# Project: Spring Social media blog API

## Background 

This project implements a backend API for a hypothetical social media application, built using the Spring Boot framework. It provides functionality for user account management and CRUD operations for messages, leveraging Spring Data JPA for database persistence.  


## Database Tables 

The following tables will are initialized in the project's built-in database upon startup using the configuration details in the application.properties file and the provided SQL script.

## Features  
- **User Registration and Login**: Secure user creation and authentication with validation and error handling.  
- **Message Management**: Endpoints for creating, retrieving, updating, and deleting messages.  
- **Relational Database Design**: Utilizes Spring Data JPA to manage `Account` and `Message` entities with seamless persistence.  

## Endpoints  
- **POST /register**: Register a new user.  
- **POST /login**: Login with existing credentials.  
- **POST /messages**: Create a new message.  
- **GET /messages**: Retrieve all messages.  
- **GET /messages/{messageId}**: Retrieve a message by ID.  
- **PATCH /messages/{messageId}**: Update message text.  
- **DELETE /messages/{messageId}**: Delete a message.  

## Prerequisites  
- Java 17+  
- Maven 3.8+  
- Spring Boot 2.7+  
- MySQL or H2 Database  

## Setup and Installation  
1. Clone the repository:  
   ```bash
   git clone https://github.com/davidqm7/Spring-Social-Media-API-
   cd spring-social-media-api
