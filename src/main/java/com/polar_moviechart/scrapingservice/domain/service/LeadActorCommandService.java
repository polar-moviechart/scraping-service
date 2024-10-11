package com.polar_moviechart.scrapingservice.domain.service;

import com.polar_moviechart.scrapingservice.domain.entity.LeadActor;
import com.polar_moviechart.scrapingservice.domain.repository.LeadActorRepository;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.LeadActorInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeadActorCommandService {

    private final LeadActorRepository leadActorRepository;

    @Transactional
    public LeadActor save(LeadActorInfoDto leadActorDto) {
        return leadActorRepository.save(leadActorDto.toEntity());
    }
}
