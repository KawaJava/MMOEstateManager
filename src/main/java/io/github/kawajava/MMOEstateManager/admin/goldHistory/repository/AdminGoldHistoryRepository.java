package io.github.kawajava.MMOEstateManager.admin.goldHistory.repository;

import io.github.kawajava.MMOEstateManager.admin.goldHistory.model.AdminGoldHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminGoldHistoryRepository extends JpaRepository<AdminGoldHistory, Long> {
    List<AdminGoldHistory> findByBoroughId(Long boroughId);
}
