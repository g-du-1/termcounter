# termcounter

## TODO
- Dockerise

In JobController.java and TermController.java, the conversion from DTO to entity is done manually using ObjectMapper. Consider using a library like MapStruct for object mapping to reduce boilerplate code and improve maintainability.

Consider using the Lombok library to reduce boilerplate code in your Java classes. Lombok provides annotations to automatically generate getters, setters, constructors, and more. For example, in your TermDTO.java, you could use @Data or @Getter and @Setter annotations to eliminate explicit getter and setter methods.

Implement API documentation using Swagger or Springdoc OpenAPI. This makes it easier to understand and test your API endpoints directly from a browser.

Instead of using @Value for injecting configuration properties, consider defining configuration properties classes using @ConfigurationProperties. This approach is type-safe and allows for better organization of your configuration properties.

## Usage

`docker run --name my-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres`
`./mvnw clean spring-boot:run`

- Run Chrome with debugger port
- Run in desktop
- Open site and pass cloudflare check
- Copy headers in to request.js from real request
- Start Server
- Run TermCounter

