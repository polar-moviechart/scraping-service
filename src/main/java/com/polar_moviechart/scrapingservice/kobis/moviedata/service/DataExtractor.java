package com.polar_moviechart.scrapingservice.kobis.moviedata.service;

import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataExtractor {

    public MovieBasicInfoDto getMovieInfo(List<WebElement> columns) {
        int rank = DataExtractUtils.convertToInt(columns.get(0).getText());
        String title = columns.get(1).getText();
        int sales = DataExtractUtils.convertToInt(columns.get(3).getText());
        int audience = DataExtractUtils.convertToInt(columns.get(7).getText());

        return new MovieBasicInfoDto(rank, title, sales, audience);
    }
}
