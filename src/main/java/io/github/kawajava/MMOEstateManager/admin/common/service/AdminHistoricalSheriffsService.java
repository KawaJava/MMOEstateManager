package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.common.utils.DateUtils;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.repository.AdminHistoricalSheriffsRepository;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.service.dto.HistoricalSheriffsFilteredDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.common.service.HistoricalSheriffsFilterService.filterHistoricalSheriffs;

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
    public List<AdminHistoricalSheriffs> getFilteredData(HistoricalSheriffsFilteredDto filteredDto) {
        var all = adminHistoricalSheriffsRepository.findAll();
        var startDateTime = DateUtils.asStartOfDay(filteredDto.startDate());
        var endDateTime = DateUtils.atEndOfDay(filteredDto.endDate());
        return filterHistoricalSheriffs(filteredDto, all, startDateTime, endDateTime);
    }
}
