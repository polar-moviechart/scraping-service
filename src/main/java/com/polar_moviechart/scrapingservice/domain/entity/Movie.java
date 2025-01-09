package com.polar_moviechart.scrapingservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "movies")
@RequiredArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private final int code;

    @Column(nullable = true)
    private String thumbnail;

    @Column(nullable = false)
    private final String title;

    @Column(nullable = false)
    private final String details;

    @Column
    private final LocalDate releaseDate;

    @Column
    private final Integer productionYear;

    @Column(nullable = false)
    private final String synopsys;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private final List<MovieDailyStats> stats = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void setThumbnail(String thumbnailPath) {
        this.thumbnail = thumbnailPath;
    }

    // 기본 생성자 추가
    public Movie() {
        this.code = 0;
        this.title = "";
        this.details = "";
        this.releaseDate = LocalDate.now();
        this.productionYear = 0;
        this.synopsys = "";
    }
}
