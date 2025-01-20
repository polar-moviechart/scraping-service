package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MovieInfoDto {
    private final int code;
    private final String title;
    private final String details;
    private final LocalDate releaseDate;
    private final Integer productionYear;
    private final String synopsys;
    private final List<String> imgUrls = new ArrayList<>();
    private boolean isSuccess;

    public Movie toEntity() {
        return Movie.builder()
                .code(code)
                .title(title)
                .details(details)
                .releaseDate(releaseDate)
                .productionYear(productionYear)
                .synopsys(synopsys)
                .isSuccess(isSuccess)
                .build();
    }

    public void addImgUrl(String imageUrl) {
        this.imgUrls.add(imageUrl);
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
