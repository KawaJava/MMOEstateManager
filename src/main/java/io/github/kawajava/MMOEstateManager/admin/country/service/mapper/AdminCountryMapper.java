package io.github.kawajava.MMOEstateManager.admin.country.service.mapper;

import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriff.model.AdminHistoricalSheriff;

import java.time.LocalDateTime;

public class AdminCountryMapper {
    public static AdminCountry mapAdminCountry(Long countryId, Long sheriffId,
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

    public static AdminHistoricalSheriff mapAdminHistoricalSheriffs(
            Long countryId, Long actualSheriffId, LocalDateTime oldSheriffStartDate, LocalDateTime now) {
        return AdminHistoricalSheriff.builder()
                .id(null)
                .countryId(countryId)
                .playerId(actualSheriffId)
                .startDate(oldSheriffStartDate)
                .endDate(now)
                .build();
    }
}
