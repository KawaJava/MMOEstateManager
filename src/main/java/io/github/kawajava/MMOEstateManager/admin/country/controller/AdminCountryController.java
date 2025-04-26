package io.github.kawajava.MMOEstateManager.admin.country.controller;

import io.github.kawajava.MMOEstateManager.admin.borough.controller.dto.AdminBoroughToAutocomplete;
import io.github.kawajava.MMOEstateManager.admin.country.controller.dto.AdminCountryDto;
import io.github.kawajava.MMOEstateManager.admin.country.controller.dto.AdminCountryGeneralInfoDto;
import io.github.kawajava.MMOEstateManager.admin.country.controller.dto.AdminCountryToAutocomplete;
import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import io.github.kawajava.MMOEstateManager.admin.country.service.AdminCountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.country.controller.mapper.AdminCountryDtoMapper.mapAdminCountryDto;
import static io.github.kawajava.MMOEstateManager.admin.utils.SlugifyUtils.slugifySlug;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/countries")
public class AdminCountryController {

    @Value("${gold.limit.default}")
    private double defaultGoldLimit;
    private final AdminCountryService adminCountryService;

    @GetMapping
    public Page<AdminCountry> getAdminCountries(Pageable pageable) {
        return adminCountryService.getAdminCountries(pageable);
    }

    @GetMapping("/list")
    @Cacheable("countriesList")
    public List<AdminCountry> getAdminCountriesAsList() {
        return adminCountryService.getAdminCountriesAsList();
    }

    @GetMapping("/{id}")
    public AdminCountry getAdminCountry(@PathVariable Long id) {
        return adminCountryService.getAdminCountry(id);
    }

    @GetMapping("/toAutocomplete")
    public List<AdminCountryToAutocomplete> getAdminCountriesToAutoComplete(@RequestParam String beginning) {
        return adminCountryService.getAdminCountriesToAutocomplete(beginning);
    }

    @PostMapping
    @CacheEvict(value = {"countries", "countriesList"}, allEntries = true)
    public AdminCountry createAdminCountry(@RequestBody @Valid AdminCountryDto adminCountryDto) {
        return adminCountryService.createAdminCountry(mapAdminCountryDto(adminCountryDto, defaultGoldLimit));
    }

    @PatchMapping("/{id}")
    @CacheEvict(value = {"countries", "countriesList"}, allEntries = true)
    public AdminCountry updateAdminCountryGeneralInfo(@PathVariable Long id,
            @RequestBody @Valid AdminCountryGeneralInfoDto adminCountryGeneralInfoDto) {
        return adminCountryService.updateAdminCountryGeneralInfo(
                mapUpdatedAdminCountry(id, adminCountryGeneralInfoDto));
    }

    @PatchMapping("/{countryId}/changeSheriff/{sheriffId}")

    @CacheEvict(value = {"countries", "countriesList"}, allEntries = true)
    public AdminCountry changeSheriff(@PathVariable Long countryId, @PathVariable Long sheriffId) {
        return adminCountryService.changeSheriff(countryId, sheriffId);
    }

    private AdminCountry mapUpdatedAdminCountry(
            Long countryId, AdminCountryGeneralInfoDto adminCountryGeneralInfoDto) {
        AdminCountry adminCountry = adminCountryService.getAdminCountry(countryId);
        return AdminCountry.builder()
                .id(countryId)
                .name(adminCountryGeneralInfoDto.getName())
                .slug(slugifySlug(adminCountryGeneralInfoDto.getSlug()))
                .actualSheriffId(adminCountry.getActualSheriffId())
                .sheriffStartDate(adminCountry.getSheriffStartDate())
                .goldLimit(adminCountry.getGoldLimit())
                .build();
    }
}
