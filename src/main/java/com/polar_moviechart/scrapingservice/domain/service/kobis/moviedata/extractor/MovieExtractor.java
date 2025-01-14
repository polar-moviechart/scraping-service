package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor;

import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieDailyStatsDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieInfoDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.ScrapingExceptionDto;
import com.polar_moviechart.scrapingservice.exception.ScrapingException;
import com.polar_moviechart.scrapingservice.utils.DataExtractUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieExtractor {

    public MovieInfoDto getMovieInfo(WebElement movieDetailPage, MovieDailyStatsDto movieDailyStatsDto) {
        String title = movieDailyStatsDto.getTitle();
        MovieInfoDto movieInfoDto;
        try {
            // 영화를 클릭하면 나오는 상세 정보 템플릿에서 영화 메타데이터 가져오기
            String titleEnglish = movieDetailPage.findElement(By.cssSelector("div.hd_layer > div"))
                    .getText()
                    .replace("영화상영관상영중", "")
                    .trim();
            title = " " + titleEnglish;

            WebElement movieInfo = movieDetailPage.findElement(By.cssSelector("dl.ovf"));
            String synopsys = movieDetailPage.findElements(By.cssSelector("p.desc_info")).stream()
                    .findFirst()
                    .map(WebElement::getText)
                    .orElse("");

            Map<String, String> dataMap = getMetadata(movieInfo);
            movieInfoDto = getMovieInfoDto(title, synopsys, dataMap);

            WebElement imageElement = movieDetailPage.findElement(By.cssSelector("a.fl.thumb img"));
            String imageUrl = imageElement.getAttribute("src");
            movieInfoDto.addImgUrl(imageUrl);
        } catch (Exception e) {
            ScrapingExceptionDto exceptionDto = new ScrapingExceptionDto();
            exceptionDto.setMovieName(title);
            return null;
//            throw new ScrapingException(e, exceptionDto);
        }
        return movieInfoDto;
    }

    private MovieInfoDto getMovieInfoDto(String title, String synopsys, Map<String, String> dataMap) {
        MovieInfoDto movieInfoDto;
        Integer code = Integer.parseInt(dataMap.get("코드"));
        String details = dataMap.get("요약정보");

        LocalDate releaseDate = Optional.ofNullable(dataMap.get("개봉일"))
                .map(DataExtractUtils::convertToLocalDate)
                .orElse(null);

        Integer productionYear = Optional.ofNullable(dataMap.get("제작연도"))
                .filter(value -> !"해당정보없음".equals(value))
                .map(this::convertToInteger)
                .orElse(null);

        movieInfoDto = new MovieInfoDto (
                code,
                title,
                details,
                releaseDate,
                productionYear,
                synopsys
        );
        return movieInfoDto;
    }

    private Map<String, String> getMetadata(WebElement movieInfo) {
        List<WebElement> metadata = movieInfo.findElements(By.cssSelector("dt, dd"));
        Map<String, String> dataMap = new HashMap<>();
        for (int keyIdx = 0; keyIdx < metadata.size() - 1; keyIdx++) {
            String key = metadata.get(keyIdx).getText();
            String value = metadata.get(keyIdx + 1).getText();
            dataMap.put(key, value);
        }
        return dataMap;
    }

    private Integer convertToInteger(String productionYear) {
        return Integer.parseInt(productionYear.replace("년", ""));
    }
}
