package io.github.kawajava.MMOEstateManager.admin.country.service;

import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import io.github.kawajava.MMOEstateManager.admin.country.repository.AdminCountryRepository;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.service.AdminHistoricalSheriffsService;
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
        return adminCountryRepository.findById(id).orElseThrow();
    }

    public AdminCountry createAdminCountry(AdminCountry adminCountry) {
        return adminCountryRepository.save(adminCountry);
    }

    public AdminCountry updateAdminCountryGeneralInfo(AdminCountry adminCountry) {
        return adminCountryRepository.save(adminCountry);
    }

    @Transactional
    public AdminCountry changeSheriff(Long countryId, Long sheriffId) {
        AdminCountry adminCountry = adminCountryRepository.findById(countryId).orElseThrow();
        LocalDateTime oldSheriffStartDate = adminCountry.getSheriffStartDate();
        Long actualSheriffId = adminCountry.getActualSheriffId();
        LocalDateTime now = LocalDateTime.now();
        AdminHistoricalSheriffs adminHistoricalSheriff = AdminHistoricalSheriffs.builder()
                .id(null)
                .countryId(countryId)
                .playerId(actualSheriffId)
                .startDate(oldSheriffStartDate)
                .endDate(now)
                .build();

        adminHistoricalSheriffsService.createAdminHistoricalSheriff(adminHistoricalSheriff);

        return adminCountryRepository.save(AdminCountry.builder()
                .id(countryId)
                .name(adminCountry.getName())
                .slug(adminCountry.getSlug())
                .goldLimit(adminCountry.getGoldLimit())
                .actualSheriffId(sheriffId)
                .sheriffStartDate(now)
                .build());
    }
}
