# API Error Center Codenation
## Running the App

- Create database `api` into Postgres data base (version 12.4)
- Open `api-error-center-codenation` and run `maven clean install` 
- Open `application.properties`, comment `spring.datasource.url=${JDBC_DATASOURCE_URL}`, uncomment `spring.datasource.url=jdbc:postgresql://localhost:5432/api?user=postgres&password=admin` and configure datasource username and password 
- Start ApiErrorCenterApplication
- The API documentation will be available on `localhost:8080/swagger-ui.html` and the endpoints on `localhost:8080/`
