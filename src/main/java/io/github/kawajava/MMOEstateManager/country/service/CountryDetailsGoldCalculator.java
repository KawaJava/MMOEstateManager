package io.github.kawajava.MMOEstateManager.country.service;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.model.Clan;
import io.github.kawajava.MMOEstateManager.country.model.Country;
import io.github.kawajava.MMOEstateManager.country.model.PlayerInfo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class CountryDetailsGoldCalculator {

    BigDecimal getAllGoldInCountry(List<Borough> countryBoroughs) {
        return countryBoroughs.stream()
                .map(Borough::getActualGold)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    BigDecimal getGoldToCollect(List<Borough> countryBoroughs, Country country) {
        return countryBoroughs.stream()
                .filter(borough -> borough.getActualGold().compareTo(country.getGoldLimit()) > 0)
                .map(borough -> borough.getActualGold().subtract(country.getGoldLimit()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    Map<Clan, BigDecimal> getGoldByClan(List<Borough> countryBoroughs, Map<Long, PlayerInfo> playerMap) {
        return countryBoroughs.stream()
                .collect(Collectors.groupingBy(
                        borough -> playerMap.get(borough.getActualLeader().getId()).getClan(),
                        Collectors.mapping(
                                Borough::getActualGold,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
    }

    Map<String, BigDecimal> getGoldByPlayers(List<Borough> countryBoroughs, Map<Long, PlayerInfo> playerMap) {
        return countryBoroughs.stream()
                .collect(Collectors.groupingBy(
                        borough -> playerMap.get(borough.getActualLeader().getId()).getName(),
                        Collectors.mapping(
                                Borough::getActualGold,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
    }

    Map<String, BigDecimal> calculatePlayerPercentage(List<Borough> countryBoroughs, Map<Long, PlayerInfo> playerMap, BigDecimal allGoldInCountry) {
        Map<String, BigDecimal> playerPercentageMap = new HashMap<>();

        countryBoroughs.stream()
                .collect(Collectors.groupingBy(borough -> borough.getActualLeader().getId(), Collectors.mapping(Borough::getActualGold, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                .forEach((leaderId, totalGoldByPlayer) -> {
                    PlayerInfo playerInfo = playerMap.get(leaderId);
                    BigDecimal percentage = totalGoldByPlayer
                            .divide(allGoldInCountry, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                    playerPercentageMap.put(playerInfo.getName(), percentage);
                });

        return playerPercentageMap;
    }
    Map<Clan, BigDecimal> calculateClanPercentage(List<Borough> countryBoroughs, Map<Long, PlayerInfo> playerMap, BigDecimal allGoldInCountry) {
        Map<Clan, BigDecimal> clanPercentageMap = new HashMap<>();

        countryBoroughs.stream()
                .collect(Collectors.groupingBy(
                        borough -> playerMap.get(borough.getActualLeader().getId()).getClan(),
                        Collectors.mapping(Borough::getActualGold, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ))
                .forEach((clan, totalGoldByClan) -> {
                    BigDecimal percentage = totalGoldByClan.divide(allGoldInCountry, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                    clanPercentageMap.put(clan, percentage);
                });

        return clanPercentageMap;
    }
}
