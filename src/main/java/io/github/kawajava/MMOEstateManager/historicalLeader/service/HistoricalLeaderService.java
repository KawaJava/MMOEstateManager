package io.github.kawajava.MMOEstateManager.historicalLeader.service;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.service.BoroughService;
import io.github.kawajava.MMOEstateManager.historicalLeader.model.HistoricalLeader;
import io.github.kawajava.MMOEstateManager.historicalLeader.repository.HistoricalLeaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricalLeaderService {

    private final HistoricalLeaderRepository historicalLeaderRepository;
    private final BoroughService boroughService;

    public List<HistoricalLeader> getHHistoricalLeadersBySlug(String slug) {
        Borough borough = boroughService.getBoroughBySlug(slug);
        return historicalLeaderRepository.findAll().stream()
                .filter(historicalLeader -> historicalLeader.getBoroughId().equals(borough.getId()))
                .toList();

    }
}
