package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor;

import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.*;
import com.polar_moviechart.scrapingservice.exception.ScrapingException;
import lombok.RequiredArgsConstructor;
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

    public MovieInfoDto getMovieInfo(WebElement movieDetailPage, MovieDailyStatsDto movieDailyStatsDto, String targetDate) {
        return movieExtractor.getMovieInfo(movieDetailPage, movieDailyStatsDto);
    }

    public MovieDailyStatsDto getMovieDailyStatsInfo(List<WebElement> columns, String targetDate) {
        return movieDailyStatsExtractor.getMovieDailyStatsInfo(columns);
    }

    public List<DirectorInfoDto> getDirectorsInfo(WebElement directorsElement, String targetDate) {
        return directorExtractor.getDirectorsInfo(directorsElement);
    }

    public List<LeadActorInfoDto> getLeadActorsInfo(WebElement leadActorsElement, String targetDate) {
        return leadActorExtractor.getLeadActorsInfo(leadActorsElement);
    }
}
