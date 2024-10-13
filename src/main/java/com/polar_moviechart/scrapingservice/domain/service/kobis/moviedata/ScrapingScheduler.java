package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ScrapingScheduler {

    private final ScrapingService scrapingService;

    public void doScrape(String startDateString, String endDateString) {
        LocalDate startDate = DataExtractUtils.convertToLocalDate(startDateString);
        LocalDate endDate = DataExtractUtils.convertToLocalDate(endDateString);

        if (startDate.isBefore(endDate)) {
            while (startDate.isBefore(endDate.plusDays(1))) {
                String targetDate = DataExtractUtils.convertString(startDate);
                scrapingService.doScrape(targetDate);
                startDate = DataExtractUtils.convertToLocalDate(targetDate).plusDays(1);
            }
        } else {
            while (startDate.isAfter(endDate.minusDays(1))) {
                String targetDate = DataExtractUtils.convertString(startDate);
                scrapingService.doScrape(targetDate);
                startDate = DataExtractUtils.convertToLocalDate(targetDate).minusDays(1);
            }
        }
    }
}
