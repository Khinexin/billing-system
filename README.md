# Test Project for Java Developer 

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [MySql](https://dev.mysql.com/downloads/mysql)

# Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.demo.billingsystem.BillingSystemApplication.java` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
$ mvn clean install
$ mvn spring-boot:run
```

## Libraries used
- Spring Boot
- Spring Web
- Spring Data JPA with Hibernate
- Spring Security with JWT Token
- MySql
- Lombok

## Configure MySQL
- Update the application.properties file in the `src/main/resources` folder with the URL, 'username' and 'password' for your MySQL instance. The table schema for the User and Student objects will be created for you in the database.

## Description


- provided postman script in the `src/main/resources/BillerAssignment.postman_collection.json` 
BillerAssignment.postman_collection.json

- initialization of SQL script in the  `src/main/resources/init-data.sql` 
init-data.sql


```shell
To generate jwt-token , 
- login url`(http://localhost:8080/login?username=admin&password=admin)` : initiated user data (username, password) 

- admin, admin
- client, client
```