## Prerequisites
### Java 17
The project is developed using java 17. It is required to compile the project.
### Docker
The project uses docker images for kafka and mysql. <br>
The app is also added to the docker compose for easier testing. <br>
Test containers are also used in integration tests.

## Running the app
### Compile (with tests)
```bash
.\mvnw clean install
```

### Compile (without tests)
```bash
.\mvnw clean install -DskipTests
```

### Run docker compose (includes building the app image)
```bash
docker-compose up --build -d
```
[NOTE] <br >
Sometimes the app may fail to start if mySQL container is not yet ready. <br >
In that case please start the application image again.

## Testing the app
### Open kafka producer console
The console producer is used to send messages in JSON format. An example of the JSON object is given below.
```bash
docker-compose exec kafka kafka-console-producer --broker-list localhost:9092 --topic test
```

An example of the JSON object.
```json
{"firstName":"John", "lastName":"Doe"}
```

A GET API is also added to retrieve saved data from the database
```api
GET http://localhost:8080/users
```

