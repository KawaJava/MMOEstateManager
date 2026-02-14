package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.common.utils.DateUtils;
import io.github.kawajava.MMOEstateManager.admin.historicalLeader.model.AdminHistoricalLeader;
import io.github.kawajava.MMOEstateManager.admin.historicalLeader.repository.AdminHistoricalLeaderRepository;
import io.github.kawajava.MMOEstateManager.admin.historicalLeader.service.dto.HistoricalLeaderFilteredDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.common.service.HistoricalLeadersFilterService.filterHistoricalLeaders;

@Service
@RequiredArgsConstructor
public class AdminHistoricalLeaderService {

    private final AdminHistoricalLeaderRepository adminHistoricalLeaderRepository;

    public Page<AdminHistoricalLeader> getAdminHistoricalLeaders(Pageable pageable) {
        return adminHistoricalLeaderRepository.findAll(pageable);
    }
    public void createAdminHistoricalLeader(AdminHistoricalLeader adminHistoricalLeader) {
        adminHistoricalLeaderRepository.save(adminHistoricalLeader);
    }
    public List<AdminHistoricalLeader> getFilteredData(HistoricalLeaderFilteredDto filteredDto) {
        var all = adminHistoricalLeaderRepository.findAll();
        var startDateTime = DateUtils.asStartOfDay(filteredDto.startDate());
        var endDateTime = DateUtils.atEndOfDay(filteredDto.endDate());
        return filterHistoricalLeaders(filteredDto, all, startDateTime, endDateTime);
    }
}
