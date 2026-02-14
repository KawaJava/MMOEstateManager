package io.github.kawajava.MMOEstateManager.admin.historicalLeader.service.dto;

import java.time.LocalDate;

public record HistoricalLeaderFilteredDto(Long boroughId, Long playerId, LocalDate startDate, LocalDate endDate) {
}
