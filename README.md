# Github repo listing api

This API lists all non-fork GitHub repositories for a given username. It utilizes the GitHub v3 API to fetch this data.
The API provides the repository names, their owner's login and details of each branch including its name and the last
commit SHA.

## External API

This application uses the [GitHub v3 API](https://developer.github.com/v3) to retrieve the necessary data.

## Getting started

1. Clone project

  ``` bash      
   git clone https://github.com/mateuszmechula/github-repo-listing.git
  ```

2. Open cloned directory

  ``` bash      
   cd github-repo-listing
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

## API Documentation with Swagger UI

Swagger UI has been integrated for better understanding and interaction with the API. You can access it via
this http://localhost:8080/swagger-ui/index.html.

## API Description

1. **List Repositories**: As a user of this API, you can provide a GitHub username and set the header "Accept:
   application/json" to fetch a list of all GitHub repositories for the given username. The response from this API will
   include the following information:
    - Repository Name: The name of each repository for the given username.
    - Owner Login: The login of the owner of each repository.
    - Branch Details: For each branch of every repository, the branch name and last commit SHA are included.

2. **Error Handling**: In a case, when a non-existing GitHub username is provided, the API will return a 404 response.
   The error response is formatted as follows:
   ```json
   {
       "status": ${responseCode},
       "message": ${whyHasItHappened}
   }
   ```

## Technologies used

### Backend

- Java 21
- Spring Boot
- Lombok
- Gradle

### Backend Testing

- JUnit
- Mockito
- RestAssured
- Wiremock

### Other

- Docker
- Swagger

## Authors

- [@Author](https://www.github.com/MateuszMechula)
