package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.exception.ScrapingException;
import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ScrapingScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ScrapingService scrapingService;

    public void doScrape(String startDateString, String endDateString) {
        LocalDate startDate = DataExtractUtils.convertToLocalDate(startDateString);
        LocalDate endDate = DataExtractUtils.convertToLocalDate(endDateString);

        try {
            if (startDate.isBefore(endDate)) {
                doScrapeForward(startDate, endDate);
            } else {
                doScrapeBackward(startDate, endDate);
            }
        } catch (ScrapingException e) {
            logger.info("=== 스크래핑 도중 예외 발생 ===");
            if (e.getExceptionDto() != null) {
                logger.info("=== 날짜: {} ===", e.getExceptionDto().getTargetDate());
                logger.info("=== 영화명: {} ===", e.getExceptionDto().getMovieName());
            }
            e.printStackTrace();
        }
    }

    private void doScrapeBackward(LocalDate startDate, LocalDate endDate) {
        scrapingService.navigateToMainPage();
        while (startDate.isAfter(endDate.minusDays(1))) {
            String targetDate = DataExtractUtils.convertString(startDate);
            scrapingService.doScrape(targetDate);
            startDate = DataExtractUtils.convertToLocalDate(targetDate).minusDays(1);
        }
    }

    private void doScrapeForward(LocalDate startDate, LocalDate endDate) {
        scrapingService.navigateToMainPage();
        while (startDate.isBefore(endDate.plusDays(1))) {
            String targetDate = DataExtractUtils.convertString(startDate);
            scrapingService.doScrape(targetDate);
            startDate = DataExtractUtils.convertToLocalDate(targetDate).plusDays(1);
        }
    }
}
