package com.gd.termcounter.selenium;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gd.termcounter.job.JobDTO;
import com.gd.termcounter.shared.Configuration;
import com.gd.termcounter.shared.ConsoleColors;
import com.gd.termcounter.term.CountTermsDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Helpers {
    private static final Logger logger = Logger.getLogger(Helpers.class.getName());
    private static final Random random = new Random();

    private Helpers() {
        throw new IllegalStateException("Utility class");
    }

    static WebDriver getWebDriver() {
        logger.log(Level.INFO, () -> ConsoleColors.GREEN + "Getting Webdriver." + ConsoleColors.RESET);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("debuggerAddress", "localhost:9222");
        return new ChromeDriver(chromeOptions);
    }

    static void randomWait() {
        long randomNumber = random.nextInt(10) + 1L;

        logger.log(Level.INFO, () -> "-------------------------------------------");
        logger.log(Level.INFO, () -> ConsoleColors.YELLOW + "Waiting for " + randomNumber + " seconds." + ConsoleColors.RESET);
        logger.log(Level.INFO, () -> "-------------------------------------------");

        try {
            Thread.sleep(randomNumber * 1000);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e::getMessage);

            Thread.currentThread().interrupt();
        }
    }

    static void clickElem(WebDriver driver, By bySelector) {
        randomWait();

        try {
            driver.findElement(bySelector).click();
        } catch (Exception e) {
            logger.log(Level.INFO, () -> bySelector + " not found. (clickElem)");
        }
    }

    static void fillInput(WebDriver driver, By bySelector, String value) {
        randomWait();

        try {
            driver.findElement(bySelector).sendKeys(value);
        } catch (Exception e) {
            logger.log(Level.INFO, () -> bySelector + " not found. (fillInput)");
        }
    }

    public static List<String> getPageUrls(int startPage, int nOfPages) {
        List<String> result = new ArrayList<>();

        int paginationNum;
        int visitedPages;

        if (startPage == 1) {
            result.add("https://uk.indeed.com/jobs?q=software+engineer");
            paginationNum = 10;
            visitedPages = 1;
        } else {
            paginationNum = (startPage - 1) * 10;
            visitedPages = 0;
        }

        while (visitedPages < nOfPages) {
            result.add("https://uk.indeed.com/jobs?q=software+engineer&start=" + paginationNum);

            paginationNum += 10;
            visitedPages++;
        }

        return result;
    }

    public static void saveJob(JobDTO dto) throws URISyntaxException {
        logger.log(Level.INFO, () -> "Saving job: " + ConsoleColors.PURPLE + dto.getTitle() + " -- " + dto.getEmployer() + ConsoleColors.RESET + '.');
        URI uri = new URI(Configuration.SAVE_JOB_URL);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<JobDTO> jobHttpEntity = new HttpEntity<>(dto, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(uri, jobHttpEntity, String.class);
    }

    public static void countTerms(CountTermsDTO dto) throws URISyntaxException {
        logger.log(Level.INFO, () -> "Counting terms.");
        URI uri = new URI(Configuration.COUNT_TERMS_URL);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CountTermsDTO> ctHttpEntity = new HttpEntity<>(dto, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(uri, ctHttpEntity, String.class);
    }

    public static JobDTO mapJsResultToDTO(Object response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(response);

        return objectMapper.readValue(jsonResponse, JobDTO.class);
    }

    public static Object getJsonJobData(WebDriver driver, WebElement jobLink) throws IOException {
        logger.log(Level.INFO, () -> "Getting JSON data for: " + ConsoleColors.PURPLE + jobLink.getAttribute("data-jk") + ConsoleColors.RESET + ".");
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String javascriptCode = new String(Files.readAllBytes(Paths.get("C:\\Users\\dudas\\termcounter\\src\\main\\java\\com\\gd\\termcounter\\selenium\\request.js")));
        javascriptCode = javascriptCode.replace("{{jobKey}}", jobLink.getAttribute("data-jk"));
        return jsExecutor.executeScript(javascriptCode);
    }
}
