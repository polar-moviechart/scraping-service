package com.polar_moviechart.scrapingservice.domain.repository;

import com.polar_moviechart.scrapingservice.domain.entity.MovieDailyStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDailyStatsRepository extends JpaRepository<MovieDailyStats, Long> {
}
