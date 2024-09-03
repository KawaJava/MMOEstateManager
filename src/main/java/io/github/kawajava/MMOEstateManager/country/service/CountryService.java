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

    public Page<Country> getCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    public Country getCountryBySlug(String slug) {
        return countryRepository.findBySlug(slug).orElseThrow();
    }

    public CountryDetails getCountryDetails(String slug) {

        var country = countryRepository.findBySlug(slug).orElseThrow();
        List<Borough> countryBoroughs = getCountryBoroughs(country.getId());

        List<Long> leadersIds = countryBoroughs.stream()
                .map(Borough::getActualLeaderId)
                .toList();

        List<Player> players = playerRepository.selectAllByIdIn(leadersIds);
        List<PlayerInfo> playerInfoList = players.stream()
                .map(player -> PlayerInfo.builder()
                        .id(player.getId())
                        .name(player.getName())
                        .clan(player.getClan())
                        .build()
                ).toList();

        BigDecimal allGoldInCountry = countryBoroughs.stream()
                .map(Borough::getActualGold)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal goldToCollect = countryBoroughs.stream()
                .filter(borough -> borough.getActualGold().compareTo(country.getGoldLimit()) > 0)
                .map(borough -> borough.getActualGold().subtract(country.getGoldLimit()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<Long, PlayerInfo> playerMap = playerInfoList.stream()
                .collect(Collectors.toMap(PlayerInfo::getId, player -> player));

        Map<String, BigDecimal> getGoldByPlayers = countryBoroughs.stream()
                .collect(Collectors.groupingBy(
                        borough -> playerMap.get(borough.getActualLeaderId()).getName(),
                        Collectors.mapping(
                                Borough::getActualGold,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
        Map<Clan, BigDecimal> getGoldByClan = countryBoroughs.stream()
                .collect(Collectors.groupingBy(
                        borough -> playerMap.get(borough.getActualLeaderId()).getClan(),
                        Collectors.mapping(
                                Borough::getActualGold,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

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
                .build();

    }

    private List<Borough> getCountryBoroughs(Long countryId) {
        return boroughRepository.findAll().stream()
                .filter(borough -> Objects.equals(borough.getCountryId(), countryId))
                .toList();
    }
}
