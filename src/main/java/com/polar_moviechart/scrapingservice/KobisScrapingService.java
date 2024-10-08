package com.polar_moviechart.scrapingservice;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class KobisScrapingService {
    private static final String targetUrl = "https://www.kobis.or.kr/kobis/business/stat/boxs/findDailyBoxOfficeList.do";

    public void moveToTargetUrl() {
        WebDriver driver = initDriver();
        driver.get(targetUrl);
    }
    private WebDriver initDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
