package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.service.dto.HistoricalLeadersFilteredDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoricalLeadersFilterService {
    public static List<AdminHistoricalLeaders> filterHistoricalLeaders(
            HistoricalLeadersFilteredDto filteredDto, List<AdminHistoricalLeaders> all,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return all.stream()
                .filter(data -> filteredDto.boroughId() == null || data.getBoroughId().equals(filteredDto.boroughId()))
                .filter(data -> filteredDto.playerId() == null || data.getPlayerId().equals(filteredDto.playerId()))
                .filter(data -> filteredDto.startDate() == null || !data.getStartDate().isBefore(startDateTime))
                .filter(data -> filteredDto.endDate() == null || !data.getEndDate().isAfter(endDateTime))
                .toList();
    }
}
