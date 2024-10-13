package com.polar_moviechart.scrapingservice.domain.repository;

import com.polar_moviechart.scrapingservice.domain.entity.MovieDailyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MovieDailyStatsRepository extends JpaRepository<MovieDailyStats, Long> {
    boolean existsByCodeAndDate(int code, LocalDate targetDate);
}
