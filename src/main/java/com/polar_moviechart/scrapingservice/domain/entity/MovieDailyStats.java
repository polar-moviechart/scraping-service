package com.polar_moviechart.scrapingservice.domain.entity;

import jakarta.persistence.Entity;

@Entity
public class MovieDailyStats {
    private final int code;
    private final int rank;
    private final int revenue;
    private final int audience;

    public MovieDailyStats(int code, int rank, int revenue, int audience) {
        this.code = code;
        this.rank = rank;
        this.revenue = revenue;
        this.audience = audience;
    }
}
