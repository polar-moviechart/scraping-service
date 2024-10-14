package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import com.polar_moviechart.scrapingservice.domain.entity.MovieDailyStats;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MovieDailyStatsDto {
    private final int code;
    private final int ranking;
    private final String title;
    private final int revenue;
    private final int audience;

    public MovieDailyStatsDto(int code, int ranking, String title, int revenue, int audience) {
        this.code = code;
        this.ranking = ranking;
        this.title = title;
        this.revenue = revenue;
        this.audience = audience;
    }

    public MovieDailyStats toEntity(LocalDate targetDate) {
        return new MovieDailyStats(
                ranking,
                revenue,
                targetDate,
                audience
        );
    }
}
