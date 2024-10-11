package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor;

import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.DirectorInfoDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.LeadActorInfoDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieDailyStatsDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieInfoDto;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class DataExtractor {

    private final MovieExtractor movieExtractor;
    private final MovieDailyStatsExtractor movieDailyStatsExtractor;
    private final DirectorExtractor directorExtractor;
    private final LeadActorExtractor leadActorExtractor;

    public static Pattern getCodePattern() {
        return Pattern.compile("mstView\\('people','(\\d+)'\\);");
    }

    public MovieInfoDto getMovieInfo(WebElement movieDetailPage, MovieDailyStatsDto movieDailyStatsDto) {
        return movieExtractor.getMovieInfo(movieDetailPage, movieDailyStatsDto);
    }

    public MovieDailyStatsDto getMovieDailyStatsInfo(List<WebElement> columns) {
        return movieDailyStatsExtractor.getMovieDailyStatsInfo(columns);
    }

    public List<DirectorInfoDto> getDirectorsInfo(WebElement directorsElement) {
        return directorExtractor.getDirectorsInfo(directorsElement);
    }

    public List<LeadActorInfoDto> getLeadActorsInfo(WebElement leadActorsElement) {
        return leadActorExtractor.getLeadActorsInfo(leadActorsElement);
    }
}
