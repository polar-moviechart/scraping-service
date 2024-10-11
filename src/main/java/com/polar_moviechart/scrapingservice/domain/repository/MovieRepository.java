package com.polar_moviechart.scrapingservice.domain.repository;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
