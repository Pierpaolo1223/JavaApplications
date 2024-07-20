Here comes some exercises based principally on asynchronous and reactive programming, microservices applications, Docker, Prometheus and 
Spring Security integration with JWT token and Postgres DB.

The "microservices-application" application is an exercise that implements and api gateway, an Eureka service (service provider), auth service (authentication microservice)
and 2 microservices for see if the request is correctly managed. All data are stored on a PostgreSQL DB and has Prometheus for capture metrics. 
For Grafana implementation, just follow the documentation for installation, then configure a data source on Prometheus, and that's it. 
For Prometheus start, just pull an image from Docker and run the following command : docker run --name prometheus -d -p 9090:9090 prom/prometheus

The "SpringAsyncRequestTest" application is an exercise that you can see how to send and stop a request using CompletableFuture.

The "SpringGraphQLTest" application is an exercise that allows to do CRUD operations using GraphQL (an alternative to REST API).

The "SpringSecurityJWTPostgresTest" application is an exercise that implements Spring Security authentication and authorization using JWT token, with data stored in a PostgreSQL
DB.

The "SpringWebFluxTest" application is like SpringAsyncRequestTest, but using WebFlux, so with this application we can have a flux of events too, beside SpringAsyncRequestTest
implementation.







