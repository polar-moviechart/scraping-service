package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MovieInfoDto {
    private final int code;
    private final String title;
    private final String details;
    private final LocalDate releaseDate;
    private final Integer productionYear;
    private final String synopsys;
    private final List<String> imgUrls = new ArrayList<>();

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

    public void addImgUrl(String imageUrl) {
        this.imgUrls.add(imageUrl);
    }
}
