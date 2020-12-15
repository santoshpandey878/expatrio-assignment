# expatrio-assignment

Implemented **"User Management Application"** using **Angular** (front-end) and **Spring Boot with Java 8** (back-end).

**Source code:**

Source code Frontend : /expatrio-assignment/front-end/user-management-application/src

Source code Backend : /expatrio-assignment/back-end/usermanagement/src



**Steps to run the application:**

You can run the application by docker compose. Below are the steps:

Created docker-compose.yml file and it is integrated with all three modules: front-end, back-end and database postgresql. When you run this script, it will fetch the database image, build the frontend and backend module, create images and start all three containers. File schema.sql is also integrated with the database using this yml file. It will create the database schema and insert the entry for userName: "admin", password: "admin" in hashed form with role "ADMIN". You can login the application by using above credentails. Once, you are logged in as admin user, you can create any number of users from the UI.

**1.** Go inside directory:     /expatrio-assignment

**2.** Execute the command:     docker-compose up

Application UI will start on port 4200 **(http://localhost:4200)** and backend server will start on port 9082 **(http://localhost:9082/usermanagement/)**.

**ADMIN User Credentials**

UserName:  admin

Password:  admin


**API Documentation**

Integrated the swagger for detailed API documentation. You can check with these URLs:

http://localhost:9082/usermanagement/swagger-ui.html

http://localhost:9082/usermanagement/v2/api-docs

**Unit and Integration tests**

Implemented few unit tests for service layer. Using TestContainer to run Integration tests out of the box in seperate container with exact production like database (postgreSQL). I’m using TestContainer so that we don’t need to have the postgreSQL database in our local system and we can run tests successfully without effecting to production database.

**Tools & Technologies Used**

Java 8

Spring Boot

Spring Data JPA

Spring Security for authentication and authorization with JWT token

PostgreSQL  

Swagger      (Used for REST API documentation)

TestContainer  (Used to run integration tests in a seperate container)

Junit, Mockito  (Used for unit tests)

RestAssured  (Used for Integration tests)

Docker Compose

Angular (Used for front end)
