package io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.service.dto;

import java.time.LocalDate;

public record HistoricalSheriffsFiltered(Long countryId, Long playerId, LocalDate startDate, LocalDate endDate) {
}
