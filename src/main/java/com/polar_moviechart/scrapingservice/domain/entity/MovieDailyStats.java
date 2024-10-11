package com.polar_moviechart.scrapingservice.domain.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private final int ranking;

    @Column(nullable = false)
    private final int revenue;

    @Column(nullable = false)
    private final int audience;

    @Column(nullable = false)
    private final LocalDate date;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public MovieDailyStats(int code, int ranking, int revenue, LocalDate date, int audience) {
        this.code = code;
        this.ranking = ranking;
        this.revenue = revenue;
        this.date = date;
        this.audience = audience;
    }
}
