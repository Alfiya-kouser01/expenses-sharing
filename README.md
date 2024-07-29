Project Overview
This project is a Spring Boot application named expenses-sharing. It uses various dependencies to create a web application with a PostgreSQL database backend.

Prerequisites
Before you start setting up the project, ensure you have the following installed on your system:

- Java 17
- Maven 3.6.0 or later
- PostgreSQL
- Git (optional, for version control)

Project Setup
--------------
1. Clone the Project
    - git clone -b main https://github.com/Alfiya-kouser01/expenses-sharing.git
    - cd expenses-sharing

2. Install PostgreSQL (Windows)
  - Download the PostgreSQL installer from the official PostgreSQL website.
  - Run the installer and follow the installation steps. Make sure to remember the username and password you set during the installation (default username is postgres).
  - During installation, ensure the PostgreSQL server is set to start automatically.
    
3. Configure the Database
  - Ensure PostgreSQL is installed and running. Create a database for the project
      - CREATE DATABASE expenses_sharing;

4. Build Project : mvn clean install

5. Run Springboot application.

6. Swagger has been enables to test the apis : http://localhost:9191/swagger-ui/index.html#/

   ![Uploading image.png…]()

