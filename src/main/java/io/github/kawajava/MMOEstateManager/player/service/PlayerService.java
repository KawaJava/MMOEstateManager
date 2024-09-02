package io.github.kawajava.MMOEstateManager.player.service;

import io.github.kawajava.MMOEstateManager.player.model.Player;
import io.github.kawajava.MMOEstateManager.player.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Page<Player> getPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    //@Transactional(readOnly = true)
    public Player getPlayerBySlug(String slug) {
        return playerRepository.findBySlug(slug).orElseThrow();
    }
}
