package io.github.kawajava.MMOEstateManager.historicalSheriffs.service;

import io.github.kawajava.MMOEstateManager.country.model.Country;
import io.github.kawajava.MMOEstateManager.country.service.CountryService;
import io.github.kawajava.MMOEstateManager.historicalSheriffs.model.HistoricalSheriffs;
import io.github.kawajava.MMOEstateManager.historicalSheriffs.repository.HistoricalSheriffsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricalSheriffsService {

    private final HistoricalSheriffsRepository historicalSheriffsRepository;
    private final CountryService countryService;

    public List<HistoricalSheriffs> getHistoricalSheriffsBySlug(String slug) {
        Country country = countryService.getCountryBySlug(slug);
        return historicalSheriffsRepository.findAll().stream()
                .filter(historicalSheriff -> historicalSheriff.getCountryId().equals(country.getId()))
                .toList();
    }
}
