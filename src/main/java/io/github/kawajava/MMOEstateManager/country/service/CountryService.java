package io.github.kawajava.MMOEstateManager.country.service;

import io.github.kawajava.MMOEstateManager.country.model.Country;
import io.github.kawajava.MMOEstateManager.common.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public Page<Country> getCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    public Country getCountryBySlug(String slug) {
        return countryRepository.findBySlug(slug).orElseThrow();
    }
}
