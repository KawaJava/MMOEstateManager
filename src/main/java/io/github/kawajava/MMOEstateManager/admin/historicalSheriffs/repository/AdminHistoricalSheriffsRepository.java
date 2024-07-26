package io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.repository;

import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminHistoricalSheriffsRepository extends
        JpaRepository<AdminHistoricalSheriffs, Long> {
}
