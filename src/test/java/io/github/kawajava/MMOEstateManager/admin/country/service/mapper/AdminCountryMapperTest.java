package io.github.kawajava.MMOEstateManager.admin.country.service.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.github.kawajava.MMOEstateManager.admin.country.service.mapper.AdminCountryMapper.mapAdminCountry;
import static io.github.kawajava.MMOEstateManager.admin.country.service.mapper.AdminCountryMapper.mapAdminHistoricalSheriffs;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AdminCountryMapperTest {

    @Test
    public void shouldMapAdminCountryCorrectly() {
        Long countryId = 1L;
        Long sheriffId = 2L;
        LocalDateTime now = LocalDateTime.now();
        AdminCountry adminCountry = AdminCountry.builder()
                .id(countryId)
                .name("Test Country")
                .slug("test-country")
                .goldLimit(BigDecimal.valueOf(1000))
                .build();

        AdminCountry result = mapAdminCountry(countryId, sheriffId, adminCountry, now);

        assertThat(result.getId()).isEqualTo(countryId);
        assertThat(result.getName()).isEqualTo("Test Country");
        assertThat(result.getSlug()).isEqualTo("test-country");
        assertThat(result.getGoldLimit()).isEqualTo(BigDecimal.valueOf(1000));
        assertThat(result.getActualSheriffId()).isEqualTo(sheriffId);
        assertThat(result.getSheriffStartDate()).isEqualTo(now);
    }

    @Test
    public void shouldMapAdminHistoricalSheriffsCorrectly() {
        Long countryId = 1L;
        Long actualSheriffId = 2L;
        LocalDateTime oldSheriffStartDate = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime now = LocalDateTime.of(2022, 2, 1, 0, 0);

        AdminHistoricalSheriffs result = mapAdminHistoricalSheriffs(countryId, actualSheriffId, oldSheriffStartDate, now);

        assertThat(result.getId()).isNull();
        assertThat(result.getCountryId()).isEqualTo(countryId);
        assertThat(result.getPlayerId()).isEqualTo(actualSheriffId);
        assertThat(result.getStartDate()).isEqualTo(oldSheriffStartDate);
        assertThat(result.getEndDate()).isEqualTo(now);
    }
}