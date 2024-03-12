package com.gd.termcounter.selenium;

import com.gd.termcounter.job.JobDTO;
import com.gd.termcounter.term.CountTermsDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.gd.termcounter.selenium.Helpers.*;
import static com.gd.termcounter.selenium.Helpers.getWebDriver;
import static com.gd.termcounter.selenium.Helpers.randomWait;

public class IndeedScraper {
    public static void main(String[] args) throws URISyntaxException, IOException {
        WebDriver driver = getWebDriver();

        List<String> pageUrls = getPageUrls(1, 2);

        for (String pageUrl : pageUrls) {
            driver.get(pageUrl);

            List<WebElement> jobLinks = driver.findElements(By.cssSelector("[data-jk]"));

            for (WebElement jobLink : jobLinks) {
                Object jobResponse = getJsonJobData(driver, jobLink);

                JobDTO jobDTO = mapJsResultToDTO(jobResponse);
                saveJob(jobDTO);

                CountTermsDTO countTermsDTO = new CountTermsDTO(jobDTO.getDescriptionTxt());
                countTerms(countTermsDTO);

                randomWait();
            }
        }

        driver.quit();
    }
}
