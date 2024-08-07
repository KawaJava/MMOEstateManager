package io.github.kawajava.MMOEstateManager.admin.country.service;

import io.github.kawajava.MMOEstateManager.admin.common.exception.ResourceNotFoundException;
import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import io.github.kawajava.MMOEstateManager.admin.country.repository.AdminCountryRepository;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import io.github.kawajava.MMOEstateManager.admin.common.service.AdminHistoricalSheriffsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminCountryService {

    private final AdminCountryRepository adminCountryRepository;
    private final AdminHistoricalSheriffsService adminHistoricalSheriffsService;

    public Page<AdminCountry> getAdminCountries(Pageable pageable) {
        return adminCountryRepository.findAll(pageable);
    }

    public AdminCountry getAdminCountry(Long id) {
        return adminCountryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country", id));
    }

    public AdminCountry createAdminCountry(AdminCountry adminCountry) {
        return adminCountryRepository.save(adminCountry);
    }

    public AdminCountry updateAdminCountryGeneralInfo(AdminCountry adminCountry) {
        return adminCountryRepository.save(adminCountry);
    }

    @Transactional
    public AdminCountry changeSheriff(Long countryId, Long sheriffId) {
        var adminCountry = adminCountryRepository.findById(countryId).orElseThrow();
        var oldSheriffStartDate = adminCountry.getSheriffStartDate();
        Long actualSheriffId = adminCountry.getActualSheriffId();
        var now = LocalDateTime.now();
        var adminHistoricalSheriff = mapAdminHistoricalSheriffs(
                countryId, actualSheriffId, oldSheriffStartDate, now);

        adminHistoricalSheriffsService.createAdminHistoricalSheriff(adminHistoricalSheriff);

        return adminCountryRepository.save(mapAdminCountry(countryId, sheriffId, adminCountry, now));
    }

    private static AdminCountry mapAdminCountry(Long countryId, Long sheriffId,
                                                AdminCountry adminCountry, LocalDateTime now) {
        return AdminCountry.builder()
                .id(countryId)
                .name(adminCountry.getName())
                .slug(adminCountry.getSlug())
                .goldLimit(adminCountry.getGoldLimit())
                .actualSheriffId(sheriffId)
                .sheriffStartDate(now)
                .build();
    }

    private static AdminHistoricalSheriffs mapAdminHistoricalSheriffs(
            Long countryId, Long actualSheriffId, LocalDateTime oldSheriffStartDate, LocalDateTime now) {
        return AdminHistoricalSheriffs.builder()
                .id(null)
                .countryId(countryId)
                .playerId(actualSheriffId)
                .startDate(oldSheriffStartDate)
                .endDate(now)
                .build();
    }
}
