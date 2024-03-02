package com.gd.termcounter.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gd.termcounter.job.JobDTO;
import org.openqa.selenium.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.gd.termcounter.selenium.Helpers.*;

public class TermCounter {
    private static int pagesVisited = 0;

    public static void main(String[] args) throws IOException, URISyntaxException {
        WebDriver driver = getWebDriver();

        // TODO Make some of this optional
        driver.get("https://indeed.com");

        clickElem(driver, By.id("onetrust-accept-btn-handler"));
        fillInput(driver, By.id("text-input-what"), "Software Engineer");
        fillInput(driver, By.id("text-input-what"), String.valueOf(Keys.RETURN));
        clickElem(driver, By.cssSelector("button[aria-label='close']"));

        int pagesToVisit = 2;

        while (pagesVisited < pagesToVisit) {
            List<WebElement> jobLinks = driver.findElements(By.cssSelector("[data-jk]"));

            for (WebElement jobLink : jobLinks) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                String javascriptCode = new String(Files.readAllBytes(Paths.get("C:\\Users\\dudas\\termcounter\\src\\main\\java\\com\\gd\\termcounter\\selenium\\request.js")));
                javascriptCode = javascriptCode.replace("{{jobKey}}", jobLink.getAttribute("data-jk"));
                Object response = jsExecutor.executeScript(javascriptCode);
                System.out.println("HTTP Response: " + response);

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(response);
                JobDTO jobDTO = objectMapper.readValue(jsonResponse, JobDTO.class);

                saveJob(jobDTO);

                randomWait();
            }

            pagesVisited++;

            clickElem(driver, By.cssSelector("[data-testid='pagination-page-next']"));
        }

        driver.quit();
    }

    private static void saveJob(JobDTO dto) throws IOException, URISyntaxException {
        URI uri = new URI("http://127.0.0.1:8080/api/v1/jobs/save");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<JobDTO> httpEntity = new HttpEntity<>(dto, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(uri, httpEntity, String.class);
    }
}
