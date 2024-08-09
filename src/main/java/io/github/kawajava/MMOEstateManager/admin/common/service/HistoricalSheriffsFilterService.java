package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.service.dto.HistoricalSheriffsFilteredDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoricalSheriffsFilterService {

    public static List<AdminHistoricalSheriffs> filterHistoricalSheriffs(
            HistoricalSheriffsFilteredDto filteredDto, List<AdminHistoricalSheriffs> all,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return all.stream()
                .filter(data -> filteredDto.countryId() == null || data.getCountryId().equals(filteredDto.countryId()))
                .filter(data -> filteredDto.playerId() == null || data.getPlayerId().equals(filteredDto.playerId()))
                .filter(data -> filteredDto.startDate() == null || !data.getStartDate().isBefore(startDateTime))
                .filter(data -> filteredDto.endDate() == null || !data.getEndDate().isAfter(endDateTime))
                .toList();
    }
}
