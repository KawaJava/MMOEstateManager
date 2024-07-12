package io.github.kawajava.MMOEstateManager.admin.player.repository;

import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdminPlayerRepository extends JpaRepository<AdminPlayer, Long> {
    @Query("update AdminPlayer p set p.isActive=false where p.id=:id")
    @Modifying
    void deactivate(Long id);
}
