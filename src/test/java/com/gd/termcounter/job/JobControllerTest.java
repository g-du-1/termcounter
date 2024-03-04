package com.gd.termcounter.job;

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
class JobControllerTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    JobRepository jobRepository;

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
        jobRepository.deleteAll();
    }

    @Test
    void shouldSaveJob() {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setKey("abcdef");
        jobDTO.setTitle("Software Engineer");
        jobDTO.setDescriptionTxt("Description Text");
        jobDTO.setEmployer("Employer");
        jobDTO.setLocation("Location");
        jobDTO.setUrl("https://example.com");
        jobDTO.setSalaryMin(45000);
        jobDTO.setSalaryMax(75000);

        given()
                .contentType(ContentType.JSON)
                .body(jobDTO)
                .when()
                .post("/api/v1/jobs/create")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("key", equalTo("abcdef"))
                .body("title", equalTo("Software Engineer"))
                .body("descriptionTxt", equalTo("Description Text"))
                .body("employer", equalTo("Employer"))
                .body("location", equalTo("Location"))
                .body("url", equalTo("https://example.com"))
                .body("salaryMin", equalTo(45000))
                .body("salaryMax", equalTo(75000));
    }

    @Test
    void updatesExistingJobWithNoDuplicates() {
        Job existingJob = new Job();
        existingJob.setKey("abcdef");
        existingJob.setTitle("Original Title");
        existingJob.setDescriptionTxt("Original Text");
        existingJob.setEmployer("Original Employer");
        existingJob.setLocation("Original Location");
        existingJob.setUrl("https://original.example.com");
        existingJob.setSalaryMin(20000);
        existingJob.setSalaryMax(30000);

        jobRepository.save(existingJob);

        JobDTO newJobWithSameKey = new JobDTO();
        newJobWithSameKey.setKey("abcdef");
        newJobWithSameKey.setTitle("Updated Title");
        newJobWithSameKey.setDescriptionTxt("Updated Text");
        newJobWithSameKey.setEmployer("Updated Employer");
        newJobWithSameKey.setLocation("Updated Location");
        newJobWithSameKey.setUrl("https://updated.example.com");
        newJobWithSameKey.setSalaryMin(70000);
        newJobWithSameKey.setSalaryMax(90000);

        given()
                .contentType(ContentType.JSON)
                .body(newJobWithSameKey)
                .when()
                .post("/api/v1/jobs/create")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("key", equalTo("abcdef"))
                .body("title", equalTo("Updated Title"))
                .body("descriptionTxt", equalTo("Updated Text"))
                .body("employer", equalTo("Updated Employer"))
                .body("location", equalTo("Updated Location"))
                .body("url", equalTo("https://updated.example.com"))
                .body("salaryMin", equalTo(70000))
                .body("salaryMax", equalTo(90000));

        List<Job> jobs = jobRepository.findAll();
        assertEquals(1, jobs.size());
    }
}