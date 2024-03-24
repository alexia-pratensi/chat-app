# Chat app
Chat-App is a proof of concept (POC) for real-time chat between a customer and an agent, intended to be part of a car rental application.

## Features

Real-time Connection: Users already registered can connect and communicate in real-time using WebSocket. The agent can switch between multiple chats with different customers.

Message History: Users can view previous message history.

User-Friendly Interface: A user-friendly and intuitive interface allows users to send and receive messages easily.


## Technologies Used

### Frontend
- Angular
- WebSocket (via SockJS and STOMP)
- HTML
- CSS

### Backend
- Java
- Spring Boot
- WebSocket (via Spring's WebSocket support)

### Database
- MySQL
- MySQL Workbench


## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17
- Maven
- Angular CLI 14 and Nodes.js
- MySQL


### Clone the Project

git clone https://github.com/alexia-pratensi/chat-app.git

### Install Dependencies

#### Frontend:

- Go inside folder: cd chat-app/frontend

- Run this command line:  npm install


#### Backend:

- Go inside folder: cd chat-app/backend

- Run this command line:  mvn clean install


### SQL setup

SQL script to create the schema is available by following this path: backend\src\main\resources\chat-db.sql

Don't forget to add your database credentials in application.properties :

- spring.datasource.username=xxxxx
  
- spring.datasource.password=xxxxx


## Run the project

### Backend:

cd chat-app/backend

Run this command line: mvn spring-boot:run

or run the app with your IDE

### Frontend:

cd chat-app/frontend

Run this command line: ng s

## Author
Alexia PRATENSI

## Overview
![image](https://github.com/alexia-pratensi/chat-app/assets/108806784/27fbe0d9-1b13-48a8-a33c-b31b44f20ee7)

Agent interface after the login
![image](https://github.com/alexia-pratensi/chat-app/assets/108806784/c4ef5edd-b37f-4312-b028-e1fdf8e26e0a)
![image](https://github.com/alexia-pratensi/chat-app/assets/108806784/42058d45-407c-496c-af8c-b7a129789625)


User interface after the login
![image](https://github.com/alexia-pratensi/chat-app/assets/108806784/658b2b6e-ec22-4308-bd9a-91b39c3298de)




  
