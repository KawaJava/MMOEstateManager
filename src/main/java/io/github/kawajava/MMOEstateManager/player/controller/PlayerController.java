package io.github.kawajava.MMOEstateManager.player.controller;

import io.github.kawajava.MMOEstateManager.country.service.CountryService;
import io.github.kawajava.MMOEstateManager.player.controller.dto.PlayerDetails;
import io.github.kawajava.MMOEstateManager.player.model.Player;
import io.github.kawajava.MMOEstateManager.player.service.dto.PlayerDto;
import io.github.kawajava.MMOEstateManager.player.service.PlayerService;
import io.github.kawajava.MMOEstateManager.user.model.User;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final CountryService countryService;

    @GetMapping
    @Cacheable("players")
    public Page<Player> getPlayers(Pageable pageable) {
        return playerService.getPlayers(pageable);
    }

    @GetMapping("/{slug}")
    public PlayerDto getPlayerBySlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
            @Length(max = 255) String slug) {
        return playerService.getPlayerBySlug(slug);
    }
    @GetMapping("/me")
    public PlayerDetails getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof User currentUser) {
            Long playerId = currentUser.getPlayerId();
            Player player = playerService.getPlayer(playerId);
            return PlayerDetails.builder()
                    .name(player.getName())
                    .email(player.getEmail())
                    .clan(player.getClan())
                    .isActive(player.isActive())
                    .created(player.getCreated())
                    .countries(countryService.getCountriesByPlayerId(playerId))
                    .build();
        }
        return null;
    }
}
