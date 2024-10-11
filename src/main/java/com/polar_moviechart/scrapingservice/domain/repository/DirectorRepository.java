package com.polar_moviechart.scrapingservice.domain.repository;

import com.polar_moviechart.scrapingservice.domain.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    boolean existsByCode(int code);
}
