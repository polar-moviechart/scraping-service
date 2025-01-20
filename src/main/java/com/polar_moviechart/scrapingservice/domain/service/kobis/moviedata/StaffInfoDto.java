package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class StaffInfoDto {
    private final List<DirectorInfoDto> directorInfoDtos;
    private final List<LeadActorInfoDto> leadActorInfoDtos;

    public StaffInfoDto(List<DirectorInfoDto> directorInfoDtos, List<LeadActorInfoDto> leadActorInfoDtos) {
        this.directorInfoDtos = directorInfoDtos;
        this.leadActorInfoDtos = leadActorInfoDtos;
    }

    public boolean isDirectorExists() {
        return !directorInfoDtos.isEmpty();
    }

    public boolean isLeadactorExists() {
        return !leadActorInfoDtos.isEmpty();
    }
}
