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
        Movie movie;
        MovieInfoDto movieInfoDto = null;
        try {
            movieInfoDto = dataExtractor.getMovieInfo(movieDetailPage, movieDailyStatsDto, targetDate);
            int movieCode = movieInfoDto.getCode();
            movie = movieCommandService.save(movieInfoDto);

            if (movie.getReleaseDate() == null && movie.getProductionYear() == null) {
                return movie;
            }

            StaffInfoDto staffInfoDto = dataExtractor.getStaffInfo(movieDetailPage, movieCode);
            staffProcessor.processStaffInfo(staffInfoDto, movieCode, targetDate);
        } catch (ScrapingException e) {
            ScrapingExceptionDto exceptionDto = new ScrapingExceptionDto();
            if (movieInfoDto != null) {
                exceptionDto.setMovieName(movieInfoDto.getTitle());
            } else {
                exceptionDto.setMovieName(e.getExceptionDto().getMovieName());
            }
            exceptionDto.setTargetDate(targetDate);
            throw new ScrapingException(e, exceptionDto);
        }
        return movie;
    }
}
