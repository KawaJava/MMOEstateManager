package io.github.kawajava.MMOEstateManager.admin.player.repository;

import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminPlayerRepository extends JpaRepository<AdminPlayer, Long> {
    @Modifying
    @Transactional
    @Query("update AdminPlayer p set p.isActive = false where p.id = :id")
    void deactivate(@Param("id") Long id);
}
