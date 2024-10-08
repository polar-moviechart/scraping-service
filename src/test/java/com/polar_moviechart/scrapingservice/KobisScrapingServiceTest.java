package com.polar_moviechart.scrapingservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KobisScrapingServiceTest {

    @Autowired private KobisScrapingService kobisScrapingService;

    @DisplayName("")
    @Test
    void test() {
        // given
        kobisScrapingService.moveToTargetUrl();
        // when

        // then
    }
}