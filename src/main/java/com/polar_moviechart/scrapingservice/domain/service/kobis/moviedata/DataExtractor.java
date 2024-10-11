package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.exception.ScrapingDataNotFoundException;
import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataExtractor {

    public MovieDailyStatsDto getMovieDailyStatsInfo(List<WebElement> columns) {
        int rank = DataExtractUtils.convertToInt(columns.get(0).getText());
        String title = columns.get(1).getText();
        int sales = DataExtractUtils.convertToInt(columns.get(3).getText());
        int audience = DataExtractUtils.convertToInt(columns.get(7).getText());

        WebElement linkElement = columns.get(1).findElement(By.cssSelector("a"));
        String onClickValue = linkElement.getAttribute("onClick");
        int code = extractMovieCode(onClickValue);

        return new MovieDailyStatsDto(code, rank, title, sales, audience);
    }

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
            throw new ScrapingDataNotFoundException("영화 코드 추출 중 예외 발생.", e);
        }
        throw new ScrapingDataNotFoundException("코드를 찾을 수 없습니다.");
    }
}
