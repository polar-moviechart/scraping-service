package com.polar_moviechart.scrapingservice.exception;

import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.ScrapingExceptionDto;

public class ScrapingException extends RuntimeException {

    private static final String msg = "스크래핑 중 문제가 발생했습니다.";
    private ScrapingExceptionDto exceptionDto;
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

    public ScrapingException(String message, Throwable cause, ScrapingExceptionDto exceptionDto) {
        super(message, cause);
        this.exceptionDto = exceptionDto;
    }

    public ScrapingException(Throwable cause, ScrapingExceptionDto exceptionDto) {
        super(msg, cause);
        cause.printStackTrace();
        this.exceptionDto = exceptionDto;
    }

    public ScrapingExceptionDto getExceptionDto() {
        return exceptionDto;
    }
}
