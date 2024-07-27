package io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.service;

import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.repository.AdminHistoricalSheriffsRepository;
import io.github.kawajava.MMOEstateManager.admin.player.controller.dto.HistoricalSheriffsFilteredDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminHistoricalSheriffsService {

    private final AdminHistoricalSheriffsRepository adminHistoricalSheriffsRepository;

    public Page<AdminHistoricalSheriffs> getAdminHistoricalSheriffs(Pageable pageable) {
        return adminHistoricalSheriffsRepository.findAll(pageable);
    }

    public void createAdminHistoricalSheriff(AdminHistoricalSheriffs adminHistoricalSheriff) {
        adminHistoricalSheriffsRepository.save(adminHistoricalSheriff);
    }

    public List<AdminHistoricalSheriffs> getFilteredData(
            HistoricalSheriffsFilteredDto filteredDto) {
        List<AdminHistoricalSheriffs> all = adminHistoricalSheriffsRepository.findAll();

        LocalDateTime startDateTime = DateUtils.asStartOfDay(filteredDto.getStartDate());
        LocalDateTime endDateTime = DateUtils.atEndOfDay(filteredDto.getEndDate());

        return all.stream()
                .filter(data -> filteredDto.getCountryId() == null ||
                        data.getCountryId().equals(filteredDto.getCountryId()))
                .filter(data -> filteredDto.getPlayerId() == null ||
                        data.getPlayerId().equals(filteredDto.getPlayerId()))
                .filter(data -> filteredDto.getStartDate() == null ||
                        ! data.getStartDate().isBefore(startDateTime))
                .filter(data -> filteredDto.getEndDate() == null ||
                        ! data.getEndDate().isAfter(endDateTime))
                .toList();
    }
}
