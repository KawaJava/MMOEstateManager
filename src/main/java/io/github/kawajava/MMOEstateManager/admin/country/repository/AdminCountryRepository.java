package io.github.kawajava.MMOEstateManager.admin.country.repository;

import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminCountryRepository extends JpaRepository<AdminCountry, Long> {
    List<AdminCountry> findTop5ByNameStartingWithIgnoreCase(String beginning);
}
