package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.common.utils.DateUtils;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.service.dto.GoldHistoryFilteredDto;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.model.AdminGoldHistory;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.repository.AdminGoldHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.common.service.AdminGoldHistoryFilterService.filterGoldHistory;

@Service
@RequiredArgsConstructor
public class AdminGoldHistoryService {

    private final AdminGoldHistoryRepository adminGoldHistoryRepository;

    public Page<AdminGoldHistory> getAdminGoldHistory(Pageable pageable) {
        return adminGoldHistoryRepository.findAll(pageable);
    }
    public List<AdminGoldHistory> getFilteredData(GoldHistoryFilteredDto filteredDto) {
        var all = adminGoldHistoryRepository.findAll();
        var startDateTime = DateUtils.asStartOfDay(filteredDto.startDate());
        var endDateTime = DateUtils.atEndOfDay(filteredDto.endDate());
        return filterGoldHistory(all, filteredDto, startDateTime, endDateTime);
    }
}
