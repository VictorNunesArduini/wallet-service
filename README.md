# wallet-service
Service responsible for managment of users wallets, enabling basic operations in their wallets, such as: creation of multiple wallet, retrieve current/histrical wallet balance, deposit/withdraw funds and transfer between wallets.

## Starting

## Para rodar esse app CLI:
- Essa aplicação usa apenas a biblioteca padrão, não sendo necessário portanto, a instalação de dependências externas.
```Bash
go run cmd/main.go < input.txt
```

### Compilação nativa:

```Bash

```

### Via Docker

```Bash

```

### Running tests

```Bash
$ ./mvnw test -PnativeTest
```

## Architectural Decisions

### Why Java with Spring Boot/Maven:

My decision of using Java was made cause is my main currently language and has a fit with Recarga pay stack, which was also my decision for using spring boot, although other modern and recent frames like Quarkus or Micronaut also could be used for this type of container microservice application. 

### Service Architecture

I chose Clean Arch cause of its ease in dealing with decoupling with its use of port/adapters interface, making it easy to change in the future, like the replacement of database types. Another reason is the simplicity to Stub/Mocking implementations, allowing a much easier setup of unit/integration tests.

## Challenges:

### Which Database should i use? 

 My choice was H2 database, that is a simple relational database for the given time scope and we can maintain a well structure between the wallets, has ACID operations that is a must requirement for transactional solutions like this and we can store the transactional history of each wallet without problems.



 ### Trade-off time scope, but will be necessary

 - H2 database for production environment is not commonly a good choice, we can replace further for a PostgreSQL solution, that is a open source database well known for his performance and multi cloud compatibility
