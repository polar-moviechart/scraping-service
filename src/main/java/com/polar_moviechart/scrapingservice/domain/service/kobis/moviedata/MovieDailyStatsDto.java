package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import lombok.Getter;

@Getter
public class MovieDailyStatsDto {
    private final int code;
    private final int rank;
    private final String title;
    private final int revenue;
    private final int audience;

    public MovieDailyStatsDto(int code, int rank, String title, int revenue, int audience) {
        this.code = code;
        this.rank = rank;
        this.title = title;
        this.revenue = revenue;
        this.audience = audience;
    }
}
