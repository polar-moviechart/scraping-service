package com.polar_moviechart.scrapingservice.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_daily_stats")
public class MovieDailyStats {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private final int code;

    @Column(nullable = false)
    private final int rank;

    @Column(nullable = false)
    private final int revenue;

    @Column(nullable = false)
    private final int audience;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public MovieDailyStats(int code, int rank, int revenue, int audience) {
        this.code = code;
        this.rank = rank;
        this.revenue = revenue;
        this.audience = audience;
    }
}
