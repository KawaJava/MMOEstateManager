package io.github.kawajava.MMOEstateManager.country.service;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.model.Clan;
import io.github.kawajava.MMOEstateManager.common.repository.BoroughRepository;
import io.github.kawajava.MMOEstateManager.country.model.Country;
import io.github.kawajava.MMOEstateManager.common.repository.CountryRepository;
import io.github.kawajava.MMOEstateManager.country.model.CountryDetails;
import io.github.kawajava.MMOEstateManager.country.model.PlayerInfo;
import io.github.kawajava.MMOEstateManager.player.model.Player;
import io.github.kawajava.MMOEstateManager.common.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final BoroughRepository boroughRepository;
    private final PlayerRepository playerRepository;
    private final CountryDetailsGoldCalculator goldCalculator;

    public Page<Country> getCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    public Country getCountryBySlug(String slug) {
        return countryRepository.findBySlug(slug).orElseThrow();
    }

    @Transactional(readOnly = true)
    public CountryDetails getCountryDetails(String slug) {

        var country = countryRepository.findBySlug(slug).orElseThrow();
        List<Borough> countryBoroughs = getCountryBoroughs(country.getId());
        List<Long> leadersIds = getLeadersIds(countryBoroughs);

        List<Player> players = playerRepository.selectAllByIdIn(leadersIds);
        List<PlayerInfo> playerInfoList = getPlayerInfoList(players);

        var allGoldInCountry = goldCalculator.getAllGoldInCountry(countryBoroughs);
        var goldToCollect = goldCalculator.getGoldToCollect(countryBoroughs, country);

        Map<Long, PlayerInfo> playerMap = playerInfoList.stream().collect(Collectors.toMap(PlayerInfo::getId, player -> player));

        Map<String, BigDecimal> getGoldByPlayers = goldCalculator.getGoldByPlayers(countryBoroughs, playerMap);
        Map<Clan, BigDecimal> getGoldByClan = goldCalculator.getGoldByClan(countryBoroughs, playerMap);

        Map<String, BigDecimal> playerPercentageMap = goldCalculator.calculatePlayerPercentage(countryBoroughs, playerMap, allGoldInCountry);
        Map<Clan, BigDecimal> clanPercentageMap = goldCalculator.calculateClanPercentage(countryBoroughs, playerMap, allGoldInCountry);

        return CountryDetails.builder()
                .id(country.getId())
                .name(country.getName())
                .slug(country.getSlug())
                .playerInfoList(playerInfoList)
                .goldLimit(country.getGoldLimit())
                .sheriffStartDate(country.getSheriffStartDate())
                .boroughCount(countryBoroughs.size())
                .boroughs(countryBoroughs)
                .allGold(allGoldInCountry)
                .goldToCollect(goldToCollect)
                .goldByPlayers(getGoldByPlayers)
                .goldByClan(getGoldByClan)
                .playerPercentage(playerPercentageMap)
                .clanPercentage(clanPercentageMap)
                .build();

    }

    private List<PlayerInfo> getPlayerInfoList(List<Player> players) {
        return players.stream()
                .map(this::mapToPlayerInfo)
                .toList();
    }

    private List<Long> getLeadersIds(List<Borough> countryBoroughs) {
        return countryBoroughs.stream()
                .map(Borough::getActualLeaderId)
                .toList();
    }

    private List<Borough> getCountryBoroughs(Long countryId) {
        return boroughRepository.findAll().stream()
                .filter(borough -> Objects.equals(borough.getCountryId(), countryId))
                .toList();
    }

    private PlayerInfo mapToPlayerInfo(Player player) {
        return PlayerInfo.builder()
                .id(player.getId())
                .name(player.getName())
                .clan(player.getClan())
                .build();
    }

}
