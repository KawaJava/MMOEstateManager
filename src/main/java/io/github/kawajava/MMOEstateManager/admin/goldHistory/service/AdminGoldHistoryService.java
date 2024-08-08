package io.github.kawajava.MMOEstateManager.admin.goldHistory.service;

import io.github.kawajava.MMOEstateManager.admin.common.utils.DateUtils;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.controller.dto.GoldHistoryFilteredDto;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.model.AdminGoldHistory;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.repository.AdminGoldHistoryRepository;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.service.dto.HistoricalLeadersFilteredDto;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.common.service.HistoricalSheriffsFilterService.filterHistoricalSheriffs;

@Service
@RequiredArgsConstructor
public class AdminGoldHistoryService {

    private final AdminGoldHistoryRepository adminGoldHistoryRepository;

    public Page<AdminGoldHistory> getAdminGoldHistory(Pageable pageable) {
        return adminGoldHistoryRepository.findAll(pageable);
    }

    public List<AdminGoldHistory> getFilteredData(GoldHistoryFilteredDto filteredDto) {
        List<AdminGoldHistory> all = adminGoldHistoryRepository.findAll();

        var startDateTime = DateUtils.asStartOfDay(filteredDto.startDate());
        var endDateTime = DateUtils.atEndOfDay(filteredDto.endDate());
        return filterGoldHistory(all, filteredDto, startDateTime, endDateTime);

    }
    public static List<AdminGoldHistory> filterGoldHistory(List<AdminGoldHistory> all,
            GoldHistoryFilteredDto filteredDto,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return all.stream()
                .filter(data -> filteredDto.boroughId() == null ||
                        data.getBoroughId().equals(filteredDto.boroughId()))
                .filter(data -> filteredDto.goldAddedBy() == null ||
                        data.getGoldAddedBy().equals(filteredDto.goldAddedBy()))
                .filter(data -> filteredDto.emailSend() == null ||
                        data.getEmailSend().equals(filteredDto.emailSend()))
                .filter(data -> filteredDto.startDate() == null ||
                        !data.getDateAdded().isBefore(startDateTime))
                .filter(data -> filteredDto.endDate() == null ||
                        !data.getDateAdded().isAfter(endDateTime))
                .toList();
    }
}
