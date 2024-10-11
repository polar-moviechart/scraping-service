package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            MovieDailyStatsDto movieDailyStatsDto = dataExtractor.getMovieDailyStatsInfo(columnInfo);

            WebElement movieDetailPage = webDriverExecutor.moveToMovieDetailPage(row);
            dataExtractor.getMovieDetailInfo(movieDetailPage, movieDailyStatsDto);
        }
    }

    private LocalDate convertToLocalDate(String targetDate) {
        String[] dateElement = targetDate.split("-");
        return LocalDate.of(
                Integer.parseInt(dateElement[0]),
                Integer.parseInt(dateElement[1]),
                Integer.parseInt(dateElement[2]));
    }


}
