package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor;

import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieDailyStatsDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieInfoDto;
import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MovieExtractor {

    public MovieInfoDto getMovieInfo(WebElement movieDetailPage, MovieDailyStatsDto movieDailyStatsDto) {
        // 영화를 클릭하면 나오는 상세 정보 템플릿에서 영화 메타데이터 가져오기
        String titleEnglish = movieDetailPage.findElement(By.cssSelector("div.hd_layer > div"))
                .getText()
                .replace("영화상영관상영중", "")
                .trim();
        String title = movieDailyStatsDto.getTitle() + " " + titleEnglish;

        WebElement movieInfo = movieDetailPage.findElement(By.cssSelector("dl.ovf"));
        String synopsys = movieDetailPage.findElement(By.cssSelector("p.desc_info")).getText();
        List<WebElement> metadata = movieInfo.findElements(By.cssSelector("dt, dd"));

        String codeString = metadata.get(1).getText();
        String details = metadata.get(7).getText();
        String releaseDateString = metadata.get(11).getText();
        String productionYear = metadata.get(13).getText();
        LocalDate releaseDate = DataExtractUtils.convertToLocalDate(releaseDateString);

        return new MovieInfoDto(
                Integer.parseInt(codeString),
                title,
                details,
                releaseDate,
                convertToInteger(productionYear),
                synopsys
        );
    }

    private Integer convertToInteger(String productionYear) {
        return Integer.parseInt(productionYear.replace("년", ""));
    }
}
