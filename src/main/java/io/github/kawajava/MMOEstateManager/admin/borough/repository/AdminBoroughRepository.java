package io.github.kawajava.MMOEstateManager.admin.borough.repository;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminBoroughRepository extends JpaRepository<AdminBorough, Long> {
    List<AdminBorough> findTop5ByNameStartingWithIgnoreCase(String beginning);

    List<AdminBorough> findAllByCountryId(Long countryId);
}
