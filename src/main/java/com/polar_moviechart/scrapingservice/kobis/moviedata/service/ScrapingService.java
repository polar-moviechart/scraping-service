package com.polar_moviechart.scrapingservice.kobis.moviedata.service;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapingService {

    private final WebDriverExecutor webDriverExecutor;
    private final DataExtractor dataExtractor;

    private void doScrape(String targetDate) {
        webDriverExecutor.navigateToPage(targetDate);
        // 더 보기 버튼 클릭
        webDriverExecutor.clickMoreButton();
        List<WebElement> movieRows = webDriverExecutor.getMovieRows();

        for (WebElement row : movieRows) {
            List<WebElement> columnInfo = webDriverExecutor.getColumnInfo(row);
            dataExtractor.getMovieInfo(columnInfo);
        }
    }


}
