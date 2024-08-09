package io.github.kawajava.MMOEstateManager.admin.country.controller.mapper;
import io.github.kawajava.MMOEstateManager.admin.country.controller.dto.AdminCountryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.github.kawajava.MMOEstateManager.admin.country.controller.mapper.AdminCountryDtoMapper.mapAdminCountryDto;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AdminCountryDtoMapperTest {

    private static final double MOCKED_DEFAULT_GOLD_LIMIT = 50000.0;

    @InjectMocks
    private AdminCountryDtoMapper adminCountryDtoMapper;

    @Test
    public void shouldMapAdminCountryCorrectly() {
        var name = "ExampleCountry";
        var slug = "example-country";
        var sheriffId = 123L;
        var goldLimit = BigDecimal.valueOf(20000);
        var now = LocalDateTime.now();

        var adminCountryDto = new AdminCountryDto(name, sheriffId, goldLimit, slug);
        var result = mapAdminCountryDto(adminCountryDto, MOCKED_DEFAULT_GOLD_LIMIT);

        assertThat(result.getId()).isNull();
        assertThat(result.getName()).isEqualTo("ExampleCountry");
        assertThat(result.getSlug()).isEqualTo("example-country");
        assertThat(result.getActualSheriffId()).isEqualTo(123L);
        assertThat(result.getGoldLimit()).isEqualTo(BigDecimal.valueOf(20000));
        assertThat(result.getSheriffStartDate()).isNotNull();
        assertThat(result.getSheriffStartDate()).isAfter(now.minusSeconds(2));
        assertThat(result.getSheriffStartDate()).isBefore(now.plusSeconds(2));
    }

    @Test
    public void shouldMapAdminCountryWithDefaultGoldLimitWhenNotProvided() {
        var name = "ExampleCountry";
        var slug = "example-country";
        var sheriffId = 123L;
        var now = LocalDateTime.now();

        var adminCountryDto = new AdminCountryDto(name, sheriffId, null, slug);
        var result = mapAdminCountryDto(adminCountryDto, MOCKED_DEFAULT_GOLD_LIMIT);

        assertThat(result.getGoldLimit()).isEqualTo(BigDecimal.valueOf(MOCKED_DEFAULT_GOLD_LIMIT));
        assertThat(result.getSlug()).isEqualTo("example-country");
        assertThat(result.getName()).isEqualTo("ExampleCountry");
        assertThat(result.getActualSheriffId()).isEqualTo(123L);
        assertThat(result.getSheriffStartDate()).isNotNull();
        assertThat(result.getSheriffStartDate()).isAfter(now.minusSeconds(2));
        assertThat(result.getSheriffStartDate()).isBefore(now.plusSeconds(2));
    }
}