package com.polar_moviechart.scrapingservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private int code;

    @Column(nullable = true)
    private String thumbnail;

    @Column(nullable = false)
    private String title;

    @Column
    private String details;

    @Column
    private LocalDate releaseDate;

    @Column
    private Integer productionYear;

    @Column
    private String synopsys;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<MovieDailyStats> stats = new ArrayList<>();

    @Column(name = "is_success", nullable = false)
    private Boolean isSuccess;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void setThumbnail(String thumbnailPath) {
        this.thumbnail = thumbnailPath;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public List<MovieDailyStats> getStats() {
        if (this.stats == null) {
            return new ArrayList<>();
        } else {
            return this.stats;
        }
    }
}
