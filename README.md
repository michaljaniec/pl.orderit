# pl.orderit
Web application for ordering service without having to contact the service provider.

## Table of contents
* [Technologies](#technologies)
* [Screenshots](#screenshots)
* [Features](#features)
* [Specified description](#Specified-description)
* [Author](#author)

## Technologies
* Java
* Spring framework (Spring Boot 2, Spring MVC, Spring Security, Spring Data JPA)
* Hibernate
* MySQL DB
* Maven
* Project Lombok
* JUnit, Mockito
* HTML
* Thymeleaf
* Bootstrap

## Screenshots
Home - landing page.

![screenshot](readme-img/home.png) <br/><br/>
Registration - for users and shop owners.

![screenshot](readme-img/registration.png) <br/><br/>
Login - login page for users.

![screenshot](readme-img/login.png) <br/><br/>
Offer - displays current offers of the services by city and shop type.

![screenshot](readme-img/offer.png) <br/><br/>
Order - allows user to order selected service.

![screenshot](readme-img/order.png) <br/><br/>
Orders - displays previous orders, allows the user to write message to the shop's owner.

![screenshot](readme-img/orders.png) <br/><br/>
Manage - displays managment panel for shop owner.

![screenshot](readme-img/serviceorders.png) <br/><br/>

## Features
* login, registration for users and shops
* find interesting shops in selected area
* create a new order
* write a message and comments regarding the order
* manage order as user or shop owner

## Specified description

The project is created using the Spring Boot 2 module from the Spring framework. The applications runs on the Tomcat server.
The frontend of the application is bassed on HTML documents using Bootstrap and Thymeleaf engine. It allows a client to interact with the backend part of the service. The user performs operations on the page, passes the data to rest controllers wich perform CRUD operations via services used for the business logic.
Backend layer interacts with the MySQL relational database using Spring Data JPA interfaces.
It uses the Hibernate framework to create and update database schemas. Spring Security is responsible for the security, session maintenance and encapsulation of the endpoints.
The source code contains implementation of basic unit and integration tests created with JUnit5 library using Mockito.


## Author
Michał Janiec
