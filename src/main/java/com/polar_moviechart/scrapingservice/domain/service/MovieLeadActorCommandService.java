package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.entity.MovieLeadactor;
import com.polar_moviechart.scrapingservice.domain.repository.MovieLeadActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieLeadActorCommandService {

    private final MovieLeadActorRepository movieLeadActorRepository;

    @Transactional
    public void save(int movieCode, int leadactorCode) {
        MovieLeadactor movieLeadactor = new MovieLeadactor(movieCode, leadactorCode);
        movieLeadActorRepository.save(movieLeadactor);
    }
}
