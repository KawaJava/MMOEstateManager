package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.goldHistory.controller.dto.GoldHistoryFilteredDto;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.model.AdminGoldHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminGoldHistoryFilterService {

    public static List<AdminGoldHistory> filterGoldHistory(List<AdminGoldHistory> all,
                                                           GoldHistoryFilteredDto filteredDto, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return all.stream()
                .filter(data -> filteredDto.boroughId() == null || data.getBoroughId().equals(filteredDto.boroughId()))
                .filter(data -> filteredDto.goldAddedBy() == null || data.getGoldAddedBy().equals(filteredDto.goldAddedBy()))
                .filter(data -> filteredDto.emailSend() == null || data.getEmailSend().equals(filteredDto.emailSend()))
                .filter(data -> filteredDto.startDate() == null || !data.getDateAdded().isBefore(startDateTime))
                .filter(data -> filteredDto.endDate() == null || !data.getDateAdded().isAfter(endDateTime))
                .toList();
    }
}
