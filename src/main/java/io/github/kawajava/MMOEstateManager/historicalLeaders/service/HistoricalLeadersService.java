package io.github.kawajava.MMOEstateManager.historicalLeaders.service;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.service.BoroughService;
import io.github.kawajava.MMOEstateManager.historicalLeaders.model.HistoricalLeaders;
import io.github.kawajava.MMOEstateManager.historicalLeaders.repository.HistoricalLeadersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricalLeadersService {

    private final HistoricalLeadersRepository historicalLeadersRepository;
    private final BoroughService boroughService;

    public List<HistoricalLeaders> getHHistoricalLeadersBySlug(String slug) {
        Borough borough = boroughService.getBoroughBySlug(slug);
        return historicalLeadersRepository.findAll().stream()
                .filter(historicalLeader -> historicalLeader.getBoroughId().equals(borough.getId()))
                .toList();

    }
}
