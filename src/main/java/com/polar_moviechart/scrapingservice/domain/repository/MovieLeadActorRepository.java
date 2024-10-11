package com.polar_moviechart.scrapingservice.domain.repository;

import com.polar_moviechart.scrapingservice.domain.MovieLeadactor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieLeadActorRepository extends JpaRepository<MovieLeadactor, Long> {
}
