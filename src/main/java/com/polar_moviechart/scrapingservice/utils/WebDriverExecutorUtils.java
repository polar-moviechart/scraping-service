package com.polar_moviechart.scrapingservice.utils;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class WebDriverExecutorUtils {

    private WebDriverExecutorUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void doEsc(WebDriver driver) {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).perform();
    }
}
