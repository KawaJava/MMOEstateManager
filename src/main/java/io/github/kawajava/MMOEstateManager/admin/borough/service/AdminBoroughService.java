package io.github.kawajava.MMOEstateManager.admin.borough.service;

import io.github.kawajava.MMOEstateManager.admin.borough.controller.dto.AdminBoroughToAutocomplete;
import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.borough.repository.AdminBoroughRepository;
import io.github.kawajava.MMOEstateManager.admin.common.exception.ResourceNotFoundException;
import io.github.kawajava.MMOEstateManager.admin.common.service.AdminHistoricalLeadersService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.borough.service.mapper.AdminBoroughMapper.mapAdminBorough;
import static io.github.kawajava.MMOEstateManager.admin.borough.service.mapper.AdminBoroughMapper.mapAdminHistoricalLeaders;

@Service
@RequiredArgsConstructor
public class AdminBoroughService {

    private final AdminBoroughRepository adminBoroughRepository;
    private final AdminHistoricalLeadersService adminHistoricalLeadersService;
    private final AdminBoroughValidationService validationService;

    public Page<AdminBorough> getAdminBoroughs(Pageable pageable) {
        return adminBoroughRepository.findAll(pageable);
    }

    public List<AdminBorough> getAdminBoroughsAsList() {
        return adminBoroughRepository.findAll();
    }

    public AdminBorough getAdminBorough(Long id) {
        return adminBoroughRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Borough", id));
    }

    public AdminBorough createAdminBorough(AdminBorough adminBorough) {
        validationService.validateUniqueConstraints(adminBorough);
        return adminBoroughRepository.save(adminBorough);
    }

    public AdminBorough updateAdminBoroughGeneralInfo(AdminBorough adminBorough) {
        validationService.validateUniqueConstraints(adminBorough);
        return adminBoroughRepository.save(adminBorough);
    }

    @Transactional
    public AdminBorough changeLeader(Long boroughId, Long leaderId) {
        var adminBorough = adminBoroughRepository.findById(boroughId).orElseThrow(() -> new ResourceNotFoundException("Borough", boroughId));
        var oldLeaderStartDate = adminBorough.getLeaderStartDate();
        var actualLeaderId = adminBorough.getActualLeaderId();
        var now = LocalDateTime.now();
        var adminHistoricalLeader = mapAdminHistoricalLeaders(boroughId, actualLeaderId, oldLeaderStartDate, now);

        adminHistoricalLeadersService.createAdminHistoricalLeader(adminHistoricalLeader);
        return adminBoroughRepository.save(mapAdminBorough(boroughId, leaderId, adminBorough, now));
    }

    public List<AdminBoroughToAutocomplete> getAdminBoroughsToAutocomplete(String beginning) {
        return adminBoroughRepository.findTop5ByNameStartingWithIgnoreCase(beginning)
                .stream()
                .map(this::mapToAdminBoroughToAutocomplete)
                .toList();
    }
    private AdminBoroughToAutocomplete mapToAdminBoroughToAutocomplete(AdminBorough adminBorough) {
        return new AdminBoroughToAutocomplete(
                adminBorough.getId(),
                adminBorough.getName(),
                adminBorough.getSlug()
        );
    }

}
