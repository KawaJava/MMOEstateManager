package io.github.kawajava.MMOEstateManager.admin.charts.service;

import io.github.kawajava.MMOEstateManager.admin.charts.model.GoldInBorough;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.model.AdminGoldHistory;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.repository.AdminGoldHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChartsService {

    private final AdminGoldHistoryRepository adminGoldHistoryRepository;

    public List<GoldInBorough> getGoldAmountGroupedByDate(Long boroughId) {

        var historyList = adminGoldHistoryRepository.findByBoroughId(boroughId);
        return historyList.stream()
            .sorted(Comparator.comparing(AdminGoldHistory::getDateAdded))
            .map(history -> new GoldInBorough(history.getDateAdded(), history.getGold()))
            .collect(Collectors.toList());
    }


}
