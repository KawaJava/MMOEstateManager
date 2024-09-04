package io.github.kawajava.MMOEstateManager.goldHistory.repository;

import io.github.kawajava.MMOEstateManager.goldHistory.model.GoldHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoldHistoryRepository extends JpaRepository<GoldHistory, Long> {
}
