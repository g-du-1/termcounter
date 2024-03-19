package com.gd.termcounter.selenium;

import com.gd.termcounter.job.JobDTO;
import com.gd.termcounter.shared.ConsoleColors;
import com.gd.termcounter.term.CountTermsDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.gd.termcounter.selenium.Helpers.*;

public class IndeedScraper {
    private static final Logger logger = Logger.getLogger(IndeedScraper.class.getName());

    public static void main(String[] args) throws URISyntaxException, IOException {
        WebDriver driver = getWebDriver();

        List<String> pageUrls = getPageUrls(1, 2);

        for (String pageUrl : pageUrls) {
            driver.get(pageUrl);

            logger.log(Level.INFO, () -> "-------------------------------------------");
            logger.log(Level.INFO, () -> ConsoleColors.CYAN + "Loading URL: " + pageUrl + '.' + ConsoleColors.RESET);
            logger.log(Level.INFO, () -> "-------------------------------------------");

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

        logger.log(Level.INFO, () -> ConsoleColors.GREEN + "Finished! Quitting driver." + ConsoleColors.RESET);
        driver.quit();
    }
}
