package com.gd.termcounter.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.URISyntaxException;
import java.util.List;

import static com.gd.termcounter.selenium.Helpers.getWebDriver;
import static com.gd.termcounter.selenium.Helpers.randomWait;

public class IndeedScraper {
    public static void main(String[] args) throws URISyntaxException {
        WebDriver driver = getWebDriver();

        List<String> pageUrls = Helpers.getPageUrls(2);

        for (String pageUrl : pageUrls) {
            driver.get(pageUrl);

            List<WebElement> jobLinks = driver.findElements(By.cssSelector("[data-jk]"));

            for (WebElement joblink : jobLinks) {

//                JobDTO jobDTO = Helpers.mapJsResultToDTO(jsResponse);
//                Helpers.saveJob(jobDTO);
            }

            randomWait();
        }


        driver.quit();
    }
}
