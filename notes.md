# Project Setup and Testing Notes

## 1. Create PostgreSQL Database:

- Create a PostgreSQL database named "loop".

## 2. Update Application Properties:

- Open `src/main/resources/application.properties`.
- Change the database connection properties to match your PostgreSQL setup:

  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/loop
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  ```

## 3. Access Swagger Documentation:

- Run your Spring Boot application.
- Open your web browser and navigate to [http://localhost:8081/swagger-ui/index.html#/](http://localhost:8081/swagger-ui/index.html#/).

## 4. Populate Data to the Database:

- Use Postman or the Swagger Docs to populate data to the database.

## 5. Test the Application:

- Execute your JUnit tests or use tools like Postman to test your RESTful endpoints.
- Check that the application functions as expected with the populated data.

## 6. Build Docker Image:


- Open a terminal and navigate to your project directory.
- Build the Docker image:

  ```bash
  docker build -t your-docker-image-name .
  ```

- Run the Docker container:

  ```bash
  docker run -p 8081:8081 your-docker-image-name
  ```

- Access your application at [http://localhost:8081](http://localhost:8081).

## Additional Notes:

- Ensure that your Docker daemon is running before building and running the Docker image.
- Make sure to replace placeholders such as `your_username` and `your_password` with your actual database credentials.

Happy coding!
