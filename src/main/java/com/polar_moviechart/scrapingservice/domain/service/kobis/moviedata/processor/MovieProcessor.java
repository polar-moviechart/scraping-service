package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.processor;

import com.polar_moviechart.scrapingservice.domain.repository.MovieRepository;
import com.polar_moviechart.scrapingservice.domain.service.MovieCommandService;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieDailyStatsDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieInfoDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.WebDriverExecutor;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieProcessor {

    private final MovieRepository movieRepository;
    private final DataExtractor dataExtractor;
    private final MovieCommandService movieCommandService;
    private final WebDriverExecutor webDriverExecutor;
    private final StaffProcessor staffProcessor;

    public void processNewMovie(WebElement movieDetailPage, MovieDailyStatsDto movieDailyStatsDto) {
        MovieInfoDto movieInfoDto = dataExtractor.getMovieInfo(movieDetailPage, movieDailyStatsDto);
        int movieCode = movieInfoDto.getCode();
        movieCommandService.save(movieInfoDto);

        List<WebElement> staffElement = webDriverExecutor.getStaffElement(movieDetailPage, movieCode);
        staffProcessor.processStaffInfo(staffElement, movieCode);
    }
}
