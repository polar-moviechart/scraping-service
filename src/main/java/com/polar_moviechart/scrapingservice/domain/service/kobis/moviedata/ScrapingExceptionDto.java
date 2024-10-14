package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import lombok.*;

@Getter
@Setter
public class ScrapingExceptionDto {

    private String targetDate;
    // 영화 이름
    private String movieName;
    // 영화감독, 혹은 주연배우 이름
    private String name;
}
