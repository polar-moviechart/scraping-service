package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ScrapingSchedulerTest {

    @Mock
    private ScrapingService scrapingServiceMock;

    @InjectMocks
    private ScrapingScheduler scrapingScheduler;

    @DisplayName("시작 날짜가 끝 날짜보다 작으면 시작 날짜와 끝 날짜가 같아질 때 까지 +1일 하면서 스크래핑한다.")
    @Test
    void doScrapeWhenStartDateBeforeEndDate() {
        Mockito.doNothing()
                .when(scrapingServiceMock)
                .doScrape(ArgumentMatchers.anyString());

        String startDateString = "2024-01-01";
        String endDateString = "2024-01-05";
        scrapingScheduler.doScrape(startDateString, endDateString);

        Mockito.verify(scrapingServiceMock, times(5)).doScrape(ArgumentMatchers.anyString());
    }

    @DisplayName("시작 날짜가 끝 날짜보다 크면 시작 날짜와 끝 날짜가 같아질 때 까지 -1일 하면서 스크래핑한다.")
    @Test
    void doScrapeWhenStartDateAfterEndDate() {
        Mockito.doNothing()
                .when(scrapingServiceMock)
                .doScrape(ArgumentMatchers.anyString());

        String startDateString = "2024-01-05";
        String endDateString = "2024-01-01";
        scrapingScheduler.doScrape(startDateString, endDateString);

        Mockito.verify(scrapingServiceMock, times(5)).doScrape(ArgumentMatchers.anyString());
    }
}