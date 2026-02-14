package io.github.kawajava.MMOEstateManager.historicalSheriff.service;

import io.github.kawajava.MMOEstateManager.country.model.Country;
import io.github.kawajava.MMOEstateManager.country.service.CountryService;
import io.github.kawajava.MMOEstateManager.historicalSheriff.model.HistoricalSheriff;
import io.github.kawajava.MMOEstateManager.historicalSheriff.repository.HistoricalSheriffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricalSheriffService {

    private final HistoricalSheriffRepository historicalSheriffRepository;
    private final CountryService countryService;

    public List<HistoricalSheriff> getHistoricalSheriffsBySlug(String slug) {
        Country country = countryService.getCountryBySlug(slug);
        return historicalSheriffRepository.findAll().stream()
                .filter(historicalSheriff -> historicalSheriff.getCountryId().equals(country.getId()))
                .toList();
    }
}
