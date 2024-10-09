package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.entity.MovieDailyStats;
import lombok.Getter;

@Getter
public class MovieBasicInfoDto {
    private final int code;
    private final int rank;
    private final String title;
    private final int revenue;
    private final int audience;

    public MovieBasicInfoDto(int code, int rank, String title, int revenue, int audience) {
        this.code = code;
        this.rank = rank;
        this.title = title;
        this.revenue = revenue;
        this.audience = audience;
    }

    public MovieDailyStats toDto() {
        return new MovieDailyStats(code, rank, revenue, audience);
    }
}
