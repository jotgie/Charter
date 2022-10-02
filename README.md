# Charter Reward Application

Charter Reward Application is a service responsible for persisting customer data, transactions
and calculation of rewarding points.

### Structure
The project consists of 3 domain modules:
- `customer`
- `reward`
- `transaction`

Each module contains:
- a `controller` - invoking an adapter   
- an `adapter` - validation of a request and invoking a service   
- a `service` - the logic

Besides, `customer` and `transaction` contain also:
- a `domain` object - has the ORM annotations
- a `repository` - jpa repository

### Build
Code can be built by gradle:
```
./gradlew clean build
```

During the build Swagger/OpenAPI specification is used to generate request/response 
classes.

Gradle build includes compilation and tests.