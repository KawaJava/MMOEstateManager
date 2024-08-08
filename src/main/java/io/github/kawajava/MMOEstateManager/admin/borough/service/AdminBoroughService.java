package io.github.kawajava.MMOEstateManager.admin.borough.service;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.borough.repository.AdminBoroughRepository;
import io.github.kawajava.MMOEstateManager.admin.common.exception.ResourceNotFoundException;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.service.AdminHistoricalLeadersService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminBoroughService {

    private final AdminBoroughRepository adminBoroughRepository;
    private final AdminHistoricalLeadersService adminHistoricalLeadersService;

    public Page<AdminBorough> getAdminBoroughs(Pageable pageable) {
        return adminBoroughRepository.findAll(pageable);
    }

    public AdminBorough getAdminBorough(Long id) {
        return adminBoroughRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Borough", id));
    }

    public AdminBorough createAdminBorough(AdminBorough adminBorough) {
        return adminBoroughRepository.save(adminBorough);
    }
    public AdminBorough updateAdminBoroughGeneralInfo(AdminBorough adminBorough) {
        return adminBoroughRepository.save(adminBorough);
    }
    @Transactional
    public AdminBorough changeLeader(Long boroughId, Long leaderId) {
        var adminBorough = adminBoroughRepository.findById(boroughId).orElseThrow(() -> new ResourceNotFoundException("Borough", boroughId));
        var oldSheriffStartDate = adminBorough.getLeaderStartDate();
        Long actualLeaderId = adminBorough.getActualLeaderId();
        var now = LocalDateTime.now();
        var adminHistoricalLeader = mapAdminHistoricalLeaders(
                leaderId, actualLeaderId, oldSheriffStartDate, now);

        adminHistoricalLeadersService.createAdminHistoricalLeader(adminHistoricalLeader);

        return adminBoroughRepository.save(mapAdminBorough(boroughId, leaderId, adminBorough, now));
    }

    private static AdminBorough mapAdminBorough(Long boroughId, Long leaderId,
                                                AdminBorough adminBorough, LocalDateTime now) {
        return AdminBorough.builder()
                .id(boroughId)
                .name(adminBorough.getName())
                .slug(adminBorough.getSlug())
                .actualGold(adminBorough.getActualGold())
                .countryId(adminBorough.getCountryId())
                .goldAddedBy(adminBorough.getGoldAddedBy())
                .actualLeaderId(leaderId)
                .leaderStartDate(now)
                .actualGold(adminBorough.getActualGold())
                .goldAddedBy(adminBorough.getGoldAddedBy())
                .dateAdded(adminBorough.getDateAdded())
                .emailSend(adminBorough.getEmailSend())
                .build();
    }

    private static AdminHistoricalLeaders mapAdminHistoricalLeaders(
            Long boroughId, Long actualLeaderId, LocalDateTime oldSheriffStartDate, LocalDateTime now) {
        return AdminHistoricalLeaders.builder()
                .id(null)
                .boroughId(boroughId)
                .playerId(actualLeaderId)
                .startDate(oldSheriffStartDate)
                .endDate(now)
                .build();
    }
}
