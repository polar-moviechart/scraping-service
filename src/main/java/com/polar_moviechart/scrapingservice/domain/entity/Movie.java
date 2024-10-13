package com.polar_moviechart.scrapingservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private final int code;

    @Column(nullable = false)
    private final String title;

    @Column(nullable = false)
    private final String details;

    @Column(nullable = false)
    private final LocalDate releaseDate;

    @Column(nullable = false)
    private final Integer productionYear;

    @Column(nullable = false)
    private final String synopsys;

    @OneToMany(mappedBy = "movie")
    private final List<MovieDailyStats> stats = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Movie(int code, String title, String details, LocalDate releaseYear, Integer productionYear, String synopsys) {
        this.code = code;
        this.title = title;
        this.details = details;
        this.releaseDate = releaseYear;
        this.productionYear = productionYear;
        this.synopsys = synopsys;
    }
}
