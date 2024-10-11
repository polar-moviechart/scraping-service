package com.polar_moviechart.scrapingservice.utls;

import java.time.LocalDate;

public class DataExtractUtils {

    // private 생성자를 추가하여 인스턴스 생성을 방지
    private DataExtractUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static int convertToInt(String numberString) {
        if (numberString == null || numberString.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(numberString.replace(",", ""));
    }

    public static LocalDate convertToLocalDate(String targetDate) {
        String[] dateElement = targetDate.split("-");
        return LocalDate.of(
                Integer.parseInt(dateElement[0]),
                Integer.parseInt(dateElement[1]),
                Integer.parseInt(dateElement[2]));
    }
}
