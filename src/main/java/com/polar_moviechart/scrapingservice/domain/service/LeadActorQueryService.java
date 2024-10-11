package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.repository.LeadActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeadActorQueryService {

    private final LeadActorRepository leadActorRepository;
    public boolean isExists(int code) {
        return leadActorRepository.existsByCode(code);
    }
}
