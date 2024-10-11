package com.polar_moviechart.scrapingservice.utls;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class DataExtractUtilsTest {

    @Test
    void convertString() {
        LocalDate localDate = LocalDate.of(2024, 10, 01);
        String localDateString = DataExtractUtils.convertString(localDate);
        assertThat(localDateString).isEqualTo("2024-10-01");
    }
}