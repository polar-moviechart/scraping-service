package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.repository.MovieRepository;
import com.polar_moviechart.scrapingservice.domain.service.*;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.processor.MovieProcessor;
import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapingService {

    private final WebDriverExecutor webDriverExecutor;
    private final DataExtractor dataExtractor;
    private final MovieDailyStatsCommandService movieDailyStatsCommandService;
    private final MovieRepository movieRepository;
    private final MovieProcessor movieProcessor;

    public void doScrape(String targetDate) {
        webDriverExecutor.navigateToPage(targetDate);
        preparePage();

        List<WebElement> movieRows = webDriverExecutor.getMovieRows();
        for (WebElement row : movieRows) {
            processMovieRow(row, targetDate);
        }
    }

    private void processMovieRow(WebElement row, String targetDate) {
        List<WebElement> columnInfo = webDriverExecutor.getColumnInfo(row);
        MovieDailyStatsDto movieDailyStatsDto = dataExtractor.getMovieDailyStatsInfo(columnInfo);

        Optional<Long> movieCodeOptional = movieRepository.findByCode(movieDailyStatsDto.getCode());
        if (movieCodeOptional.isEmpty()) {
            WebElement movieDetailPage = webDriverExecutor.moveToMovieDetailPage(row);
            movieProcessor.processNewMovie(movieDetailPage, movieDailyStatsDto);
        }

        movieDailyStatsCommandService.save(movieDailyStatsDto, DataExtractUtils.convertToLocalDate(targetDate));
    }

    private void preparePage() {
        webDriverExecutor.clickMoreButton();  // 더 보기 버튼 클릭
    }
}
