package io.github.kawajava.MMOEstateManager.historicalSheriff.repository;

import io.github.kawajava.MMOEstateManager.historicalSheriff.model.HistoricalSheriff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalSheriffRepository extends JpaRepository<HistoricalSheriff, Long> {
}
