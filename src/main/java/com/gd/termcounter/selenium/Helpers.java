package com.gd.termcounter.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
}
