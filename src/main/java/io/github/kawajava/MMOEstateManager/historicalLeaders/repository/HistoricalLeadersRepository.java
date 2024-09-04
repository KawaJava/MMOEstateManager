package io.github.kawajava.MMOEstateManager.historicalLeaders.repository;

import io.github.kawajava.MMOEstateManager.historicalLeaders.model.HistoricalLeaders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalLeadersRepository extends JpaRepository<HistoricalLeaders, Long> {
}
