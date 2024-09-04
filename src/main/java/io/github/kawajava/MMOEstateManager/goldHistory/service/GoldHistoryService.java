package io.github.kawajava.MMOEstateManager.goldHistory.service;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.service.BoroughService;
import io.github.kawajava.MMOEstateManager.goldHistory.model.GoldHistory;
import io.github.kawajava.MMOEstateManager.common.repository.GoldHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoldHistoryService {

    private final GoldHistoryRepository goldHistoryRepository;
    private final BoroughService boroughService;

    public List<GoldHistory> getHistoryGoldsByBoroughSlug(String slug) {
        Borough borough = boroughService.getBoroughBySlug(slug);
        return goldHistoryRepository.findAll().stream()
                .filter(goldHistory -> goldHistory.getBoroughId().equals(borough.getId()))
                .toList();
    }
}
