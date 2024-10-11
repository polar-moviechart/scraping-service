package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MovieInfoDto {
    private final int code;
    private final String title;
    private final String details;
    private final LocalDate releaseDate;
    private final Integer productionYear;
    private final String synopsys;

    public MovieInfoDto(int code, String title, String details, LocalDate releaseDate, Integer productionYear, String synopsys) {
        this.code = code;
        this.title = title;
        this.details = details;
        this.releaseDate = releaseDate;
        this.productionYear = productionYear;
        this.synopsys = synopsys;
    }

    public Movie toEntity() {
        return new Movie(
                code,
                title,
                details,
                releaseDate,
                productionYear,
                synopsys
        );
    }
}
