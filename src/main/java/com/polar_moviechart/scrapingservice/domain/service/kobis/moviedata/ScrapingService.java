package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import com.polar_moviechart.scrapingservice.domain.entity.MovieDailyStats;
import com.polar_moviechart.scrapingservice.domain.repository.MovieRepository;
import com.polar_moviechart.scrapingservice.domain.service.*;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.processor.MovieProcessor;
import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final MovieDailyStatsQueryService movieDailyStatsQueryService;

    @Transactional
    public void doScrape(String targetDate) {
        webDriverExecutor.navigateToPage(targetDate);
        preparePage();

        List<WebElement> movieRows = webDriverExecutor.getMovieRows();
        for (WebElement row : movieRows) {
            processMovieRow(row, targetDate);
        }
    }

    @Transactional
    private void processMovieRow(WebElement row, String targetDate) {
        List<WebElement> columnInfo = webDriverExecutor.getColumnInfo(row);
        MovieDailyStatsDto movieDailyStatsDto = dataExtractor.getMovieDailyStatsInfo(columnInfo);

        Optional<Movie> movieOptional = movieRepository.findByCode(movieDailyStatsDto.getCode());
        Movie movie = movieOptional.orElseGet(() -> {
            WebElement movieDetailPage = webDriverExecutor.moveToMovieDetailPage(row);
            Movie createdMovie = movieProcessor.processNewMovie(movieDetailPage, movieDailyStatsDto);
            movieDetailPage.findElement(By.cssSelector("div.hd_layer > a.close")).click();
            return createdMovie;
        });

        boolean existsByCodeAndDate = movieDailyStatsQueryService.isExists(movieDailyStatsDto.getCode(), DataExtractUtils.convertToLocalDate(targetDate));
        if (!existsByCodeAndDate) {
            MovieDailyStats movieDailyStats = movieDailyStatsDto.toEntity(DataExtractUtils.convertToLocalDate(targetDate));
            movieDailyStats.setMovie(movie);
            movieDailyStatsCommandService.save(movieDailyStats);
        }
    }

    private void preparePage() {
        webDriverExecutor.clickMoreButton();  // 더 보기 버튼 클릭
    }
}
