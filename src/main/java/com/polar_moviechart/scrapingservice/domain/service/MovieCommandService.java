package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import com.polar_moviechart.scrapingservice.domain.repository.MovieRepository;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.MovieInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieCommandService {

    private final MovieRepository movieRepository;
    public Movie save(MovieInfoDto movieInfoDto) {
        return movieRepository.save(movieInfoDto.toEntity());
    }
}
