package io.github.kawajava.MMOEstateManager.player.repository;

import io.github.kawajava.MMOEstateManager.player.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findBySlug(String slug);
}
