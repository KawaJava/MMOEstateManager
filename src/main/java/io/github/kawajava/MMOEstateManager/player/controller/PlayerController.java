package io.github.kawajava.MMOEstateManager.player.controller;

import io.github.kawajava.MMOEstateManager.player.model.Player;
import io.github.kawajava.MMOEstateManager.player.service.PlayerService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
@Validated
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public Page<Player> getPlayers(Pageable pageable) {
        return playerService.getPlayers(pageable);
    }

    @GetMapping("/{slug}")
    public Player getPlayerBySlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
            @Length(max = 255) String slug) {
        return playerService.getPlayerBySlug(slug);
    }
}
