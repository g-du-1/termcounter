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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class Helpers {
    private static final Logger logger = LoggerFactory.getLogger(Helpers.class);
    private static final Random random = new Random();

    private Helpers() {
        throw new IllegalStateException("Utility class");
    }

    static WebDriver getWebDriver() {
        logger.info(ConsoleColors.GREEN + "Getting Webdriver." + ConsoleColors.RESET);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("debuggerAddress", "localhost:9222");
        return new ChromeDriver(chromeOptions);
    }

    static void randomWait() {
        long randomNumber = random.nextInt(10) + 1L;

        logger.info(ConsoleColors.YELLOW + "Waiting for {} seconds." + ConsoleColors.RESET, randomNumber);

        try {
            Thread.sleep(randomNumber * 1000);
        } catch (InterruptedException e) {
            logger.info(e.toString());

            Thread.currentThread().interrupt();
        }
    }

    static void clickElem(WebDriver driver, By bySelector) {
        randomWait();

        try {
            driver.findElement(bySelector).click();
        } catch (Exception e) {
            logger.info("{} not found. (clickElem)", bySelector);
        }
    }

    static void fillInput(WebDriver driver, By bySelector, String value) {
        randomWait();

        try {
            driver.findElement(bySelector).sendKeys(value);
        } catch (Exception e) {
            logger.info("{} not found.", bySelector);
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
        logger.info(ConsoleColors.PURPLE + "Saving job {} -- {}." + ConsoleColors.RESET, dto.getTitle(), dto.getEmployer());
        URI uri = new URI(Configuration.SAVE_JOB_URL);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<JobDTO> jobHttpEntity = new HttpEntity<>(dto, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(uri, jobHttpEntity, String.class);
    }

    public static void countTerms(CountTermsDTO dto) throws URISyntaxException {
        logger.info(ConsoleColors.PURPLE + "Counting terms." + ConsoleColors.RESET);
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
        String jobKey = jobLink.getAttribute("data-jk");
        logger.info(ConsoleColors.PURPLE + "Getting JSON data for: {}." + ConsoleColors.RESET, jobKey);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String javascriptCode = new String(Files.readAllBytes(Paths.get("C:\\Users\\dudas\\termcounter\\src\\main\\java\\com\\gd\\termcounter\\selenium\\request.js")));
        javascriptCode = javascriptCode.replace("{{jobKey}}", jobKey);
        return jsExecutor.executeScript(javascriptCode);
    }
}
