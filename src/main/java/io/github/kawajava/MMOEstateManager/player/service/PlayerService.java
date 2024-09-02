package io.github.kawajava.MMOEstateManager.player.service;


import io.github.kawajava.MMOEstateManager.common.repository.CountryRepository;
import io.github.kawajava.MMOEstateManager.player.model.Player;
import io.github.kawajava.MMOEstateManager.player.repository.PlayerRepository;
import io.github.kawajava.MMOEstateManager.player.service.dto.CountryDto;
import io.github.kawajava.MMOEstateManager.player.service.dto.PlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final CountryRepository countryRepository;

    public Page<Player> getPlayers(Pageable pageable) {
        //return playerRepository.findAll(pageable);
        return playerRepository.findAllByIsActiveTrueOrderByName(pageable);
    }

    @Transactional(readOnly = true)
    public PlayerDto getPlayerBySlug(String slug) {
        var player = playerRepository.findBySlug(slug).orElseThrow();
        var countries = countryRepository.findAllByActualSheriffId(player.getId());
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .email(player.getEmail())
                .slug(player.getSlug())
                .clan(player.getClan())
                .created(player.getCreated())
                .countryDtos(countries.stream()
                        .map(country -> CountryDto.builder()
                                .id(country.getId())
                                .name(country.getName())
                                .slug(country.getSlug())
                                .goldLimit(country.getGoldLimit())
                                .sheriffStartDate(country.getSheriffStartDate())
                                .build())
                        .toList())
                .build();
    }
}
