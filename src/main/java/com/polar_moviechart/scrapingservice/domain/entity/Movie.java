package com.polar_moviechart.scrapingservice.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class Movie {
    private final int id;
    private final int code;
    private final String title;

    public Movie(int id, int code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }
}
