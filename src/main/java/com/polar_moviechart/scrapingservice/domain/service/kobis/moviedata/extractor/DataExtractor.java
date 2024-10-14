package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor;

import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.*;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataExtractor {

    private final MovieExtractor movieExtractor;
    private final MovieDailyStatsExtractor movieDailyStatsExtractor;
    private final DirectorExtractor directorExtractor;
    private final LeadActorExtractor leadActorExtractor;

    public MovieInfoDto getMovieInfo(WebElement movieDetailPage, MovieDailyStatsDto movieDailyStatsDto) {
        return movieExtractor.getMovieInfo(movieDetailPage, movieDailyStatsDto);
    }

    public MovieDailyStatsDto getMovieDailyStatsInfo(List<WebElement> columns) {
        return movieDailyStatsExtractor.getMovieDailyStatsInfo(columns);
    }

    public StaffInfoDto getStaffInfo(WebElement movieDetailPage, Integer movieCode) {
        // 코드 아이디로 감독, 배우 정보가 있는 staff 관련 태그 얻어내기
        String staffId = movieCode + "_staff";
        WebElement staffInfo = movieDetailPage.findElement(By.id(staffId));
        WebElement descriptionInfo = staffInfo.findElement(By.cssSelector("dl.desc_info"));

        List<WebElement> staffElements = descriptionInfo.findElements(By.cssSelector("div"));

        WebElement directorElement = descriptionInfo.findElement(By.cssSelector("div[id$='director']"));
        List<DirectorInfoDto> directorsInfo = directorExtractor.getDirectorsInfo(directorElement);
        if (staffElements.size() == 1) {
            return new StaffInfoDto(directorsInfo, List.of());
        }
        List<LeadActorInfoDto> leadActorsInfo = leadActorExtractor.getLeadActorsInfo(staffElements.get(1));
        return new StaffInfoDto(directorsInfo, leadActorsInfo);
    }
}
