package io.github.kawajava.MMOEstateManager.historicalSheriffs.repository;

import io.github.kawajava.MMOEstateManager.historicalSheriffs.model.HistoricalSheriffs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalSheriffsRepository extends JpaRepository<HistoricalSheriffs, Long> {
}
