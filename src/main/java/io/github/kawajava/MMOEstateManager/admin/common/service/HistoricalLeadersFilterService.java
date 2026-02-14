package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.historicalLeader.model.AdminHistoricalLeader;
import io.github.kawajava.MMOEstateManager.admin.historicalLeader.service.dto.HistoricalLeaderFilteredDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoricalLeadersFilterService {
    public static List<AdminHistoricalLeader> filterHistoricalLeaders(
            HistoricalLeaderFilteredDto filteredDto, List<AdminHistoricalLeader> all,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return all.stream()
                .filter(data -> filteredDto.boroughId() == null || data.getBoroughId().equals(filteredDto.boroughId()))
                .filter(data -> filteredDto.playerId() == null || data.getPlayerId().equals(filteredDto.playerId()))
                .filter(data -> filteredDto.startDate() == null || !data.getStartDate().isBefore(startDateTime))
                .filter(data -> filteredDto.endDate() == null || !data.getEndDate().isAfter(endDateTime))
                .toList();
    }
}
