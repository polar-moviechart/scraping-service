package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.processor;

import com.polar_moviechart.scrapingservice.domain.service.*;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.DirectorInfoDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.LeadActorInfoDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StaffProcessor {

    private final DataExtractor dataExtractor;
    private final DirectorQueryService directorQueryService;
    private final DirectorCommandService directorCommandService;
    private final LeadActorQueryService leadActorQueryService;
    private final LeadActorCommandService leadActorCommandService;
    private final MovieDirectorCommandService movieDirectorCommandService;
    private final MovieLeadActorCommandService movieLeadActorCommandService;
    private final MovieDirectorQueryService movieDirectorQueryService;
    private final MovieLeadActorQueryService movieLeadActorQueryservice;

    public void processStaffInfo(List<WebElement> staffElement, int movieCode) {
        List<DirectorInfoDto> directorsDto = dataExtractor.getDirectorsInfo(staffElement.get(0));
        for (DirectorInfoDto directorDto : directorsDto) {
            if (!directorQueryService.isExists(directorDto.getCode())) {
                directorCommandService.save(directorDto);
            }
            if (!movieDirectorQueryService.isExists(movieCode, directorDto.getCode())) {
                movieDirectorCommandService.save(movieCode, directorDto.getCode());
            }
        }

        List<LeadActorInfoDto> leadActorsDto = dataExtractor.getLeadActorsInfo(staffElement.get(1));
        for (LeadActorInfoDto leadActorDto : leadActorsDto) {
            if (!leadActorQueryService.isExists(leadActorDto.getCode())) {
                leadActorCommandService.save(leadActorDto);
            }
            if (!movieLeadActorQueryservice.isExists(movieCode, leadActorDto.getCode())) {
                movieLeadActorCommandService.save(movieCode, leadActorDto.getCode());
            }
        }
    }
}
