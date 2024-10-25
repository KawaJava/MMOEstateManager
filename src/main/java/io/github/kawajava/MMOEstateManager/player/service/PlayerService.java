package io.github.kawajava.MMOEstateManager.player.service;


import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.repository.BoroughRepository;
import io.github.kawajava.MMOEstateManager.common.repository.CountryRepository;
import io.github.kawajava.MMOEstateManager.country.model.Country;
import io.github.kawajava.MMOEstateManager.player.model.Player;
import io.github.kawajava.MMOEstateManager.common.repository.PlayerRepository;
import io.github.kawajava.MMOEstateManager.player.service.dto.BoroughDto;
import io.github.kawajava.MMOEstateManager.player.service.dto.CountryDto;
import io.github.kawajava.MMOEstateManager.player.service.dto.PlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final CountryRepository countryRepository;
    private final BoroughRepository boroughRepository;

    public Page<Player> getPlayers(Pageable pageable) {
        return playerRepository.findAllByIsActiveTrueOrderByName(pageable);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public PlayerDto getPlayerBySlug(String slug) {

        var player = playerRepository.findBySlug(slug).orElseThrow();
        var countries = countryRepository.findAllByActualSheriffId(player.getId());
        var boroughs = boroughRepository.findAllByActualLeaderId(player.getId());

        return buildPlayerDto(player, countries, boroughs);
    }

    private PlayerDto buildPlayerDto(Player player, List<Country> countries, List<Borough> boroughs) {
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .email(player.getEmail())
                .slug(player.getSlug())
                .clan(player.getClan())
                .created(player.getCreated())
                .countryDtos(mapCountriesToDtos(countries))
                .boroughDtos(mapBoroughsToDtos(boroughs))
                .build();
    }

    private List<BoroughDto> mapBoroughsToDtos(List<Borough> boroughs) {
        return boroughs.stream()
                .map(borough -> BoroughDto.builder()
                        .id(borough.getId())
                        .name(borough.getName())
                        .slug(borough.getSlug())
                        .countryId(borough.getCountryId())
                        .leaderStartDate(borough.getLeaderStartDate())
                        .build())
                .toList();
    }

    private List<CountryDto> mapCountriesToDtos(List<Country> countries) {
        return countries.stream()
                .map(country -> CountryDto.builder()
                        .id(country.getId())
                        .name(country.getName())
                        .slug(country.getSlug())
                        .goldLimit(country.getGoldLimit())
                        .sheriffStartDate(country.getSheriffStartDate())
                        .build())
                .toList();
    }
}
