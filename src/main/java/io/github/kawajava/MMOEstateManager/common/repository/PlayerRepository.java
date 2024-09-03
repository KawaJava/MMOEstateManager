package io.github.kawajava.MMOEstateManager.common.repository;

import io.github.kawajava.MMOEstateManager.player.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findBySlug(String slug);

    Page<Player> findAllByIsActiveTrueOrderByName(Pageable pageable);

    @Query("select p from Player p where p.id in (:ids)")
    @Modifying
    List<Player> selectAllByIdIn(List<Long> ids);
}
