package com.gd.termcounter.term;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TermControllerTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    TermRepository termRepository;

    @LocalServerPort
    private Integer port;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        termRepository.deleteAll();
    }

    @Test
    void shouldSaveTerm() {
        TermDTO termDTO = new TermDTO();
        termDTO.setName("term");
        termDTO.setCount(5);

        given()
            .contentType(ContentType.JSON)
            .body(termDTO)
        .when()
            .post("/api/v1/terms/save")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo("term"))
            .body("count", equalTo(5));
    }

    @Test
    void shouldNotSaveTermWithNoName() {
        TermDTO termDTO = new TermDTO();
        termDTO.setCount(5);

        given()
            .contentType(ContentType.JSON)
            .body(termDTO)
        .when()
            .post("/api/v1/terms/save")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", equalTo("Field 'name' cannot be blank"));
    }

    @Test
    void shouldSaveCountAsOneIfNotProvided() {
        TermDTO termDTO = new TermDTO();
        termDTO.setName("term");

        given()
            .contentType(ContentType.JSON)
            .body(termDTO)
        .when()
            .post("/api/v1/terms/save")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo("term"))
            .body("count", equalTo(1));
    }

    @Test
    void updatesExistingTermWithNoDuplicates() {
        Term existingTerm = new Term();
        existingTerm.setName("term");
        existingTerm.setCount(6);

        termRepository.save(existingTerm);

        TermDTO newTerm = new TermDTO();
        newTerm.setName("term");
        newTerm.setCount(9);

        given()
            .contentType(ContentType.JSON)
            .body(newTerm)
        .when()
            .post("/api/v1/terms/save")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo("term"))
            .body("count", equalTo(9));

        List<Term> terms = termRepository.findAll();
        assertEquals(1, terms.size());
    }

    @Test
    void countsTerms() {
        CountTermsDTO countTermsDTO = new CountTermsDTO();
        countTermsDTO.setDescription("description. (job) Job\n\njob, and and, the a");

        given()
            .contentType(ContentType.JSON)
            .body(countTermsDTO)
        .when()
            .post("/api/v1/terms/count")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("[0].name", equalTo("description"))
            .body("[0].count", equalTo(1))
            .body("[1].name", equalTo("job"))
            .body("[1].count", equalTo(3));

         List<Term> terms = termRepository.findAll();
         assertEquals(2, terms.size());
    }
}