package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.common.utils.DateUtils;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriff.model.AdminHistoricalSheriff;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriff.repository.AdminHistoricalSheriffRepository;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriff.service.dto.HistoricalSheriffFilteredDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.common.service.HistoricalSheriffFilterService.filterHistoricalSheriffs;

@Service
@RequiredArgsConstructor
public class AdminHistoricalSheriffService {

    private final AdminHistoricalSheriffRepository adminHistoricalSheriffRepository;

    public Page<AdminHistoricalSheriff> getAdminHistoricalSheriffs(Pageable pageable) {
        return adminHistoricalSheriffRepository.findAll(pageable);
    }
    public void createAdminHistoricalSheriff(AdminHistoricalSheriff adminHistoricalSheriff) {
        adminHistoricalSheriffRepository.save(adminHistoricalSheriff);
    }
    public List<AdminHistoricalSheriff> getFilteredData(HistoricalSheriffFilteredDto filteredDto) {
        var all = adminHistoricalSheriffRepository.findAll();
        var startDateTime = DateUtils.asStartOfDay(filteredDto.startDate());
        var endDateTime = DateUtils.atEndOfDay(filteredDto.endDate());
        return filterHistoricalSheriffs(filteredDto, all, startDateTime, endDateTime);
    }
}
