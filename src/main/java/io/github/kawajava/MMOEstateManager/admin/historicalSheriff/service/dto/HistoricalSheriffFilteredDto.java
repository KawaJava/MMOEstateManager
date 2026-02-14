package io.github.kawajava.MMOEstateManager.admin.historicalSheriff.service.dto;

import java.time.LocalDate;

public record HistoricalSheriffFilteredDto(Long countryId, Long playerId, LocalDate startDate, LocalDate endDate) {
}
