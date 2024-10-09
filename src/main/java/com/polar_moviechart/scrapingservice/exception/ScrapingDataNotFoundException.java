package com.polar_moviechart.scrapingservice.exception;

public class ScrapingDataNotFoundException extends RuntimeException {

    private static final String msg = "스크래핑 중 문제가 발생했습니다..";
    public ScrapingDataNotFoundException() {
        super(msg);
    }

    public ScrapingDataNotFoundException(String message) {
        super(message);
    }

    public ScrapingDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScrapingDataNotFoundException(Throwable cause) {
        super(cause);
    }
}
