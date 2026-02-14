package io.github.kawajava.MMOEstateManager.admin.historicalLeader.repository;

import io.github.kawajava.MMOEstateManager.admin.historicalLeader.model.AdminHistoricalLeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminHistoricalLeaderRepository extends JpaRepository<AdminHistoricalLeader, Long> {
}
