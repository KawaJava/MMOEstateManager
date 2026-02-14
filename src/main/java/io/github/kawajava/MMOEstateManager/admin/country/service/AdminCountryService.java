package io.github.kawajava.MMOEstateManager.admin.country.service;

import io.github.kawajava.MMOEstateManager.admin.common.exception.ResourceNotFoundException;
import io.github.kawajava.MMOEstateManager.admin.country.controller.dto.AdminCountryToAutocomplete;
import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import io.github.kawajava.MMOEstateManager.admin.country.repository.AdminCountryRepository;
import io.github.kawajava.MMOEstateManager.admin.common.service.AdminHistoricalSheriffService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.country.service.mapper.AdminCountryMapper.mapAdminCountry;
import static io.github.kawajava.MMOEstateManager.admin.country.service.mapper.AdminCountryMapper.mapAdminHistoricalSheriffs;

@Service
@RequiredArgsConstructor
public class AdminCountryService {

    private final AdminCountryRepository adminCountryRepository;
    private final AdminHistoricalSheriffService adminHistoricalSheriffService;
    private final AdminCountryValidationService validationService;

    public Page<AdminCountry> getAdminCountries(Pageable pageable) {
        return adminCountryRepository.findAll(pageable);
    }

    public List<AdminCountry> getAdminCountriesAsList() {
        return adminCountryRepository.findAll();
    }

    public AdminCountry getAdminCountry(Long id) {
        return adminCountryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country", id));
    }

    public AdminCountry createAdminCountry(AdminCountry adminCountry) {
        validationService.validateUniqueConstraints(adminCountry);
        return adminCountryRepository.save(adminCountry);
    }

    public AdminCountry updateAdminCountryGeneralInfo(AdminCountry adminCountry) {
        validationService.validateUniqueConstraints(adminCountry);
        return adminCountryRepository.save(adminCountry);
    }

    @Transactional
    public AdminCountry changeSheriff(Long countryId, Long sheriffId) {
        var adminCountry = adminCountryRepository.findById(countryId).orElseThrow(() -> new ResourceNotFoundException("Country", countryId));
        var oldSheriffStartDate = adminCountry.getSheriffStartDate();
        var actualSheriffId = adminCountry.getActualSheriffId();
        var now = LocalDateTime.now();
        var adminHistoricalSheriff = mapAdminHistoricalSheriffs(countryId, actualSheriffId, oldSheriffStartDate, now);

        adminHistoricalSheriffService.createAdminHistoricalSheriff(adminHistoricalSheriff);
        return adminCountryRepository.save(mapAdminCountry(countryId, sheriffId, adminCountry, now));
    }

    public List<AdminCountryToAutocomplete> getAdminCountriesToAutocomplete(String beginning) {
        return adminCountryRepository.findTop5ByNameStartingWithIgnoreCase(beginning)
                .stream()
                .map(this::mapToAdminCountryToAutocomplete)
                .toList();
    }

    private AdminCountryToAutocomplete mapToAdminCountryToAutocomplete(AdminCountry adminCountry) {
        return new AdminCountryToAutocomplete(
                adminCountry.getId(),
                adminCountry.getName()
        );
    }
}
