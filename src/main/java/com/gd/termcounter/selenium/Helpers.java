package com.gd.termcounter.selenium;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gd.termcounter.job.JobDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helpers {
    static WebDriver getWebDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("debuggerAddress", "localhost:9222");
        return new ChromeDriver(chromeOptions);
    }

    static void randomWait() {
        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1;
        System.out.println("Waiting for " + randomNumber + " seconds...");

        try {
            Thread.sleep(randomNumber * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Done waiting!");
    }

    static void clickElem(WebDriver driver, By bySelector) {
        randomWait();

        try {
            driver.findElement(bySelector).click();
        } catch (Exception e) {
            System.out.println(bySelector + " not found. (clickElem)");
        }
    }

    static void fillInput(WebDriver driver, By bySelector, String value) {
        randomWait();

        try {
            driver.findElement(bySelector).sendKeys(value);
        } catch (Exception e) {
            System.out.println(bySelector + " not found. (fillInput)");
        }
    }

    public static List<String> getPageUrls(int nOfPages) {
        List<String> result = new ArrayList<>();

        result.add("https://uk.indeed.com/jobs?q=software+engineer");

        for (int i = 1; i < nOfPages; i++) {
            result.add("https://uk.indeed.com/jobs?q=software+engineer&start=" + i * 10);
        }

        return result;
    }

    public static void saveJob(JobDTO dto) throws URISyntaxException {
        URI uri = new URI("http://127.0.0.1:8080/api/v1/jobs/save");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<JobDTO> jobHttpEntity = new HttpEntity<>(dto, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(uri, jobHttpEntity, String.class);
    }

    public static JobDTO mapJsResultToDTO(Object response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(response);

        return objectMapper.readValue(jsonResponse, JobDTO.class);
    }
}
