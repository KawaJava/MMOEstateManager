package io.github.kawajava.MMOEstateManager.country.repository;

import io.github.kawajava.MMOEstateManager.country.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findBySlug(String slug);
}
