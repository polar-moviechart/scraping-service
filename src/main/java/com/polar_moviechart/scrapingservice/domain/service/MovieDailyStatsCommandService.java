package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import com.polar_moviechart.scrapingservice.domain.repository.MovieDailyStatsRepository;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieDailyStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MovieDailyStatsCommandService {

    private final MovieDailyStatsRepository movieDailyStatsRepository;

    public void save(Movie movie, MovieDailyStatsDto movieDailyStatsDto, LocalDate targetDate) {
        movieDailyStatsRepository.save(movieDailyStatsDto.toEntity(movie, targetDate));
    }
}
