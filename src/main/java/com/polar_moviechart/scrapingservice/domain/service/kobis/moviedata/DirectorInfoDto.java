package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.entity.Director;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DirectorInfoDto {

    private final int code;
    private final String name;

    public Director toEntity() {
        return new Director(code, name);
    }
}
