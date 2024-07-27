package io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.service.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class HistoricalSheriffsFilteredDto {
    private Long countryId;
    private Long playerId;
    private LocalDate startDate;
    private LocalDate endDate;
}
