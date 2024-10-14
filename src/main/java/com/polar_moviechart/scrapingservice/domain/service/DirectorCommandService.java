package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.entity.Director;
import com.polar_moviechart.scrapingservice.domain.repository.DirectorRepository;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.DirectorInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DirectorCommandService {

    private final DirectorRepository directorRepository;

    public Director save(DirectorInfoDto directorInfoDto) {
        return directorRepository.save(directorInfoDto.toEntity());
    }
}
