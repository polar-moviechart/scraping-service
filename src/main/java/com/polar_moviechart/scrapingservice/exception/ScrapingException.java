package com.polar_moviechart.scrapingservice.exception;

public class ScrapingException extends RuntimeException {

    private static final String msg = "스크래핑 중 문제가 발생했습니다..";
    public ScrapingException() {
        super(msg);
    }

    public ScrapingException(String message) {
        super(message);
    }

    public ScrapingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScrapingException(Throwable cause) {
        super(cause);
    }
}
