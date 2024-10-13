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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private final int ranking;

    @Column(nullable = false)
    private final int revenue;

    @Column(nullable = false)
    private final int audience;

    @Column(nullable = false)
    private final LocalDate date;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY) // ManyToOne 관계 설정
    @JoinColumn(name = "movie_code", nullable = false) // 외래 키 설정
    private Movie movie;

    public void setMovie(Movie movie) {
        this.movie = movie;
        movie.getStats().add(this);
    }

    public MovieDailyStats(int ranking, int revenue, LocalDate date, int audience, Movie movie) {
        this.ranking = ranking;
        this.revenue = revenue;
        this.date = date;
        this.audience = audience;
        setMovie(movie);
    }
}
