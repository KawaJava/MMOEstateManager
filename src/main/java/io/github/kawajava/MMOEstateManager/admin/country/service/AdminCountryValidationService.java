package io.github.kawajava.MMOEstateManager.admin.country.service;

import io.github.kawajava.MMOEstateManager.admin.common.exception.adminCountry.CountryNameAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminCountry.CountrySlugAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import io.github.kawajava.MMOEstateManager.admin.country.repository.AdminCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCountryValidationService {

    private final AdminCountryRepository adminCountryRepository;

    public void validateUniqueConstraints(AdminCountry countryToValidate) {
        List<AdminCountry> all = adminCountryRepository.findAll();

        all.stream()
                .filter(c -> countryToValidate.getId() == null || !c.getId().equals(countryToValidate.getId()))
                .forEach(c -> {
                    if (c.getName().equalsIgnoreCase(countryToValidate.getName())) {
                        throw new CountryNameAlreadyExistsException(countryToValidate.getName());
                    }
                    if (c.getSlug().equalsIgnoreCase(countryToValidate.getSlug())) {
                        throw new CountrySlugAlreadyExistsException(countryToValidate.getSlug());
                    }
                });
    }
}
