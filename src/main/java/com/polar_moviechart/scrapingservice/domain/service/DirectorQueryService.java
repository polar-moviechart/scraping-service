package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.repository.DirectorRepository;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.DirectorInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorQueryService {

    private final DirectorRepository directorRepository;

    public boolean isExists(int code) {
        return directorRepository.existsByCode(code);
    }

    public void save(DirectorInfoDto directorDto) {
        directorRepository.save(directorDto.toEntity());
    }
}
