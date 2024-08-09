package io.github.kawajava.MMOEstateManager.admin.country.controller.mapper;

import io.github.kawajava.MMOEstateManager.admin.country.controller.dto.AdminCountryDto;
import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.github.kawajava.MMOEstateManager.admin.utils.SlugifyUtils.slugifySlug;

public class AdminCountryDtoMapper {

    public static AdminCountry mapAdminCountryDto(AdminCountryDto adminCountryDto, double defaultGoldLimit) {
        return AdminCountry.builder()
                .id(null)
                .name(adminCountryDto.getName())
                .slug(slugifySlug(adminCountryDto.getSlug()))
                .actualSheriffId(adminCountryDto.getActualSheriffId())
                .goldLimit(adminCountryDto.getGoldLimit() == null ? BigDecimal.valueOf(defaultGoldLimit) :
                        adminCountryDto.getGoldLimit())
                .sheriffStartDate(LocalDateTime.now())
                .build();
    };
}
