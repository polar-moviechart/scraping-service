package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.processor;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import com.polar_moviechart.scrapingservice.domain.service.MovieCommandService;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.*;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import com.polar_moviechart.scrapingservice.exception.ScrapingException;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieProcessor {
    private final DataExtractor dataExtractor;
    private final MovieCommandService movieCommandService;
    private final StaffProcessor staffProcessor;

    @Transactional
    public Movie processNewMovie(WebElement movieDetailPage, MovieDailyStatsDto movieDailyStatsDto, String targetDate) {
        MovieInfoDto movieInfoDto = dataExtractor.getMovieInfo(movieDetailPage, movieDailyStatsDto);
        int movieCode = movieInfoDto.getCode();
        Movie movie = movieCommandService.save(movieInfoDto);
        try {
            if (movie.getReleaseDate() == null && movie.getProductionYear() == null) {
                return movie;
            }
            StaffInfoDto staffInfoDto = dataExtractor.getStaffInfo(movieDetailPage, movieCode);
            staffProcessor.processStaffInfo(staffInfoDto, movieCode, targetDate);
        } catch (Exception e) {
            ScrapingExceptionDto exceptionDto = new ScrapingExceptionDto();
            exceptionDto.setMovieName(movie.getTitle());
            exceptionDto.setTargetDate(targetDate);
            throw new ScrapingException(e, exceptionDto);
        }
        return movie;
    }
}
