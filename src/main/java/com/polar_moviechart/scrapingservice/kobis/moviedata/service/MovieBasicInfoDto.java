package com.polar_moviechart.scrapingservice.kobis.moviedata.service;

import lombok.Getter;

@Getter
public class MovieBasicInfoDto {
    private final int rank;
    private final String title;
    private final int sales;
    private final int audience;

    public MovieBasicInfoDto(int rank, String title, int sales, int audience) {
        this.rank = rank;
        this.title = title;
        this.sales = sales;
        this.audience = audience;
    }
}
