package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.entity.LeadActor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LeadActorInfoDto {
    private final int code;
    private final String name;

    public LeadActor toEntity() {
        return new LeadActor(code, name);
    }
}
