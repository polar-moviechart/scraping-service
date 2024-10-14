package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor;

import com.polar_moviechart.scrapingservice.domain.entity.MovieDailyStats;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieDailyStatsDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.ScrapingExceptionDto;
import com.polar_moviechart.scrapingservice.exception.ScrapingException;
import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MovieDailyStatsExtractor {

    public MovieDailyStatsDto getMovieDailyStatsInfo(List<WebElement> columns) {
        String title = columns.get(1).getText();
        MovieDailyStatsDto dailyStatsDto = null;
        try {
            int rank = DataExtractUtils.convertToInt(columns.get(0).getText());
            int sales = DataExtractUtils.convertToInt(columns.get(3).getText());
            int audience = DataExtractUtils.convertToInt(columns.get(7).getText());

            WebElement linkElement = columns.get(1).findElement(By.cssSelector("a"));
            String onClickValue = linkElement.getAttribute("onClick");
            int code = extractMovieCode(onClickValue);

            dailyStatsDto = new MovieDailyStatsDto(code, rank, title, sales, audience);
        } catch (Exception e) {
            ScrapingExceptionDto exceptionDto = new ScrapingExceptionDto();
            exceptionDto.setMovieName(title);
            throw new ScrapingException(e, exceptionDto);
        }

        return dailyStatsDto;
    }

    private int extractMovieCode(String onClickValue) {
        // 정규 표현식 패턴
        Pattern pattern = Pattern.compile("mstView\\('movie','(\\d+)'\\)");
        Matcher matcher = pattern.matcher(onClickValue);

        // 코드 찾기
        try {
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception e) {
            throw new ScrapingException("영화 코드 추출 중 예외 발생.", e);
        }
        throw new ScrapingException("코드를 찾을 수 없습니다.");
    }
}
