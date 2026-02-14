package io.github.kawajava.MMOEstateManager.historicalLeader.repository;

import io.github.kawajava.MMOEstateManager.historicalLeader.model.HistoricalLeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalLeaderRepository extends JpaRepository<HistoricalLeader, Long> {
}
