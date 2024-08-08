package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.common.utils.DateUtils;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.repository.AdminHistoricalLeadersRepository;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.service.dto.HistoricalLeadersFilteredDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.common.service.HistoricalLeadersFilterService.filterHistoricalLeaders;

@Service
@RequiredArgsConstructor
public class AdminHistoricalLeadersService {

    private final AdminHistoricalLeadersRepository adminHistoricalLeadersRepository;

    public Page<AdminHistoricalLeaders> getAdminHistoricalLeaders(Pageable pageable) {
        return adminHistoricalLeadersRepository.findAll(pageable);
    }
    public void createAdminHistoricalLeader(AdminHistoricalLeaders adminHistoricalLeader) {
        adminHistoricalLeadersRepository.save(adminHistoricalLeader);
    }
    public List<AdminHistoricalLeaders> getFilteredData(
            HistoricalLeadersFilteredDto filteredDto) {
        List<AdminHistoricalLeaders> all = adminHistoricalLeadersRepository.findAll();
        var startDateTime = DateUtils.asStartOfDay(filteredDto.startDate());
        var endDateTime = DateUtils.atEndOfDay(filteredDto.endDate());
        return filterHistoricalLeaders(filteredDto, all, startDateTime, endDateTime);
    }
}
