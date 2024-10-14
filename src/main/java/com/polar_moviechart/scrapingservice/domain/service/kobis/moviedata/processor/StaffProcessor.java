package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.processor;

import com.polar_moviechart.scrapingservice.domain.service.*;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.StaffInfoDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffProcessor {

    private final DirectorQueryService directorQueryService;
    private final DirectorCommandService directorCommandService;
    private final LeadActorQueryService leadActorQueryService;
    private final LeadActorCommandService leadActorCommandService;
    private final MovieDirectorCommandService movieDirectorCommandService;
    private final MovieLeadActorCommandService movieLeadActorCommandService;
    private final MovieDirectorQueryService movieDirectorQueryService;
    private final MovieLeadActorQueryService movieLeadActorQueryservice;

    @Transactional
    public void processStaffInfo(StaffInfoDto staffInfoDto, int movieCode, String targetDate) {

        if (staffInfoDto.isDirectorExists()) {
            staffInfoDto.getDirectorInfoDtos().stream()
                    .filter(directorDto -> !directorQueryService.isExists(directorDto.getCode()))
                    .forEach(directorCommandService::save);

            staffInfoDto.getDirectorInfoDtos().stream()
                    .filter(directorDto -> !movieDirectorQueryService.isExists(movieCode, directorDto.getCode()))
                    .forEach(directorDto -> movieDirectorCommandService.save(movieCode, directorDto.getCode()));
        }

        if (staffInfoDto.isLeadactorExists()) {
            staffInfoDto.getLeadActorInfoDtos().stream()
                    .filter(leadActorDto -> !leadActorQueryService.isExists(leadActorDto.getCode()))
                    .forEach(leadActorDto -> leadActorCommandService.save(leadActorDto));

            staffInfoDto.getLeadActorInfoDtos().stream()
                    .filter(leadActorDto -> !movieLeadActorQueryservice.isExists(movieCode, leadActorDto.getCode()))
                    .forEach(leadActorDto -> movieLeadActorCommandService.save(movieCode, leadActorDto.getCode()));
        }
    }
}
