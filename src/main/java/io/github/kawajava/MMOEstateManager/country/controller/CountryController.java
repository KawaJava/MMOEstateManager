package io.github.kawajava.MMOEstateManager.country.controller;

import io.github.kawajava.MMOEstateManager.country.model.Country;
import io.github.kawajava.MMOEstateManager.country.model.CountryDetails;
import io.github.kawajava.MMOEstateManager.country.service.CountryService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
@Validated
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public Page<Country> getCountries(Pageable pageable) {
        return countryService.getCountries(pageable);
    }

    @GetMapping("/{slug}")
    public Country getCountryBySlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                  @Length(max = 255) String slug) {
        return countryService.getCountryBySlug(slug);
    }

    @GetMapping("/{slug}/details")
    public CountryDetails getCountryDetails(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                    @Length(max = 255) String slug) {
        return countryService.getCountryDetails(slug);
    }
}
