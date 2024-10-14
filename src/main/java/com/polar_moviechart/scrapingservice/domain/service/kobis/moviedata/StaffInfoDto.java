package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class StaffInfoDto {
    private final List<DirectorInfoDto> directorInfoDtos;
    private final List<LeadActorInfoDto> leadActorInfoDtos;

    public boolean isDirectorExists() {
        return !directorInfoDtos.isEmpty();
    }

    public boolean isLeadactorExists() {
        return !leadActorInfoDtos.isEmpty();
    }
}
