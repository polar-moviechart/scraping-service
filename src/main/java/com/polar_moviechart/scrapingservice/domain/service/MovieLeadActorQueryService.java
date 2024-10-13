package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.repository.MovieLeadActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieLeadActorQueryService {
    private final MovieLeadActorRepository movieLeadActorRepository;


    public boolean isExists(int movieCode, int leadactorCode) {
        return movieLeadActorRepository.existsByMovieCodeAndLeadactorCode(movieCode, leadactorCode);
    }
}
