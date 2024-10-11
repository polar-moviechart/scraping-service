package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.entity.MovieDirector;
import com.polar_moviechart.scrapingservice.domain.repository.MovieDirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieDirectorCommandService {
    private final MovieDirectorRepository movieDirectorRepository;

    public MovieDirector save(int movieCode, int code) {
        MovieDirector movieDirector = new MovieDirector(movieCode, code);
        return movieDirectorRepository.save(movieDirector);
    }
}
