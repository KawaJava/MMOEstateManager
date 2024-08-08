package io.github.kawajava.MMOEstateManager.admin.historicalLeaders.repository;

import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminHistoricalLeadersRepository extends JpaRepository<AdminHistoricalLeaders, Long> {
}
