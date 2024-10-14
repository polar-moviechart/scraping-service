package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.repository.MovieDailyStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MovieDailyStatsQueryService {
    private final MovieDailyStatsRepository movieDailyStatsRepository;

    public boolean isExists(int movieCode, LocalDate targetDate) {
        return movieDailyStatsRepository.existsByMovieCodeAndDate(movieCode, targetDate);
    }
}
