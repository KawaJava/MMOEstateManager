package io.github.kawajava.MMOEstateManager.admin.country.controller;

import io.github.kawajava.MMOEstateManager.admin.country.controller.dto.AdminCountryDto;
import io.github.kawajava.MMOEstateManager.admin.country.controller.dto.AdminCountryGeneralInfoDto;
import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import io.github.kawajava.MMOEstateManager.admin.country.service.AdminCountryService;
import io.github.kawajava.MMOEstateManager.admin.player.controller.dto.AdminPlayerDto;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.github.kawajava.MMOEstateManager.admin.utils.SlugifyUtils.slugifySlug;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/countries")
public class AdminCountryController {

    private final AdminCountryService adminCountryService;

    @GetMapping
    public Page<AdminCountry> getAdminCountries(Pageable pageable) {
        return adminCountryService.getAdminCountries(pageable);
    }

    @GetMapping("/{id}")
    public AdminCountry getAdminCountry(@PathVariable Long id) {
        return adminCountryService.getAdminCountry(id);
    }

    @PostMapping
    public AdminCountry createAdminCountry(@RequestBody @Valid AdminCountryDto adminCountryDto) {
        return adminCountryService.createAdminCountry(mapAdminCountry(adminCountryDto));
    }

    @PutMapping("/{id}")
    public AdminCountry updateAdminCountryGeneralInfo(@PathVariable Long id,
            @RequestBody @Valid AdminCountryGeneralInfoDto adminCountryGeneralInfoDto) {
        return adminCountryService.updateAdminCountryGeneralInfo(
                mapUpdatedAdminCountry(id, adminCountryGeneralInfoDto));
    }

    private AdminCountry mapUpdatedAdminCountry(
            Long countryId, AdminCountryGeneralInfoDto adminCountryGeneralInfoDto) {
        AdminCountry adminCountry = adminCountryService.getAdminCountry(countryId);
        return AdminCountry.builder()
                .id(countryId)
                .name(adminCountryGeneralInfoDto.getName())
                .slug(adminCountryGeneralInfoDto.getSlug())
                .actualSheriffId(adminCountry.getActualSheriffId())
                .sheriffStartDate(adminCountry.getSheriffStartDate())
                .goldLimit(adminCountry.getGoldLimit())
                .build();
    }

    private AdminCountry mapAdminCountry(AdminCountryDto adminCountryDto) {
        return AdminCountry.builder()
                .id(null)
                .name(adminCountryDto.getName())
                .slug(slugifySlug(adminCountryDto.getSlug()))
                .actualSheriffId(adminCountryDto.getActualSheriffId())
                .goldLimit(adminCountryDto.getGoldLimit() == null ? BigDecimal.valueOf(50000) :
                        adminCountryDto.getGoldLimit())
                .sheriffStartDate(LocalDateTime.now())
                .build();
    };

}
