# Shopping Cart Project

This is a Spring Boot-based backend project for managing a shopping cart. It provides a RESTful API to manage the cart, including adding, updating, and removing items, as well as managing the cart's overall state.

## Technologies Used
*   Java (Version 25)
*   Spring Boot (Version 4.0.3)
*   Maven

## How to Run

### Prerequisites
*   Java 25 installed and configured on your system.
*   Maven

### Running Locally

1. **Navigate to the project directory:**
    Open a terminal and navigate to the project root directory where the `pom.xml` is located.

2. **Build the project:**
    Use the Maven wrapper to clean and build the executable JAR file.
    ```bash
    ./mvnw clean install
    ```
    *(If you are on Windows, use `./mvnw.cmd clean install`)*

3. **Run the Application:**
    You can run the application directly using the Spring Boot Maven plugin:
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, if you have built the application, you can run the JAR file located in the `target/` directory:
    ```bash
    java -jar target/ShoppingCart\ -0.0.1-SNAPSHOT.jar
    ```
    for testing you can run 
    mvn test

The server should start successfully on `http://localhost:8080`.
