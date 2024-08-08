package io.github.kawajava.MMOEstateManager.admin.historicalLeaders.service.dto;

import java.time.LocalDate;

public record HistoricalLeadersFilteredDto(Long boroughId, Long playerId, LocalDate startDate, LocalDate endDate) {
}
