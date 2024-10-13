package com.polar_moviechart.scrapingservice.domain.repository;

import com.polar_moviechart.scrapingservice.domain.entity.MovieLeadactor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieLeadActorRepository extends JpaRepository<MovieLeadactor, Long> {
    boolean existsByMovieCodeAndLeadactorCode(int movieCode, int leadactorCode);
}
