package com.gd.termcounter.selenium;

import com.gd.termcounter.job.JobDTO;
import com.gd.termcounter.shared.ConsoleColors;
import com.gd.termcounter.term.CountTermsDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.gd.termcounter.selenium.Helpers.*;

public class IndeedScraper {
    private static final Logger logger = LoggerFactory.getLogger(IndeedScraper.class);

    public static void main(String[] args) throws URISyntaxException, IOException {
        WebDriver driver = getWebDriver();

        List<String> pageUrls = getPageUrls(1, 2);

        for (String pageUrl : pageUrls) {
            driver.get(pageUrl);

            logger.info(ConsoleColors.CYAN + "Loading URL: {}" + ConsoleColors.RESET, pageUrl);

            randomWait();

            List<WebElement> jobLinks = driver.findElements(By.cssSelector("[data-jk]"));

            for (WebElement jobLink : jobLinks) {
                Object jobResponse = getJsonJobData(driver, jobLink);

                JobDTO jobDTO = mapJsResultToDTO(jobResponse);
                CountTermsDTO countTermsDTO = new CountTermsDTO(jobDTO.getDescriptionTxt(), jobDTO.getKey());

                countTerms(countTermsDTO);
                saveJob(jobDTO);

                randomWait();
            }
        }

        logger.info(ConsoleColors.GREEN + "Finished! Quitting driver." + ConsoleColors.RESET);
        driver.quit();
    }
}
