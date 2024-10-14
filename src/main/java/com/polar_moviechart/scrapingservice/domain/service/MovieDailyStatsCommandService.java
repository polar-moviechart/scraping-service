package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.entity.MovieDailyStats;
import com.polar_moviechart.scrapingservice.domain.repository.MovieDailyStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieDailyStatsCommandService {

    private final MovieDailyStatsRepository movieDailyStatsRepository;

    @Transactional
    public void save(MovieDailyStats stats) {
        movieDailyStatsRepository.save(stats);
    }
}
