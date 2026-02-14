package io.github.kawajava.MMOEstateManager.admin.historicalSheriff.repository;

import io.github.kawajava.MMOEstateManager.admin.historicalSheriff.model.AdminHistoricalSheriff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminHistoricalSheriffRepository extends
        JpaRepository<AdminHistoricalSheriff, Long> {
}
