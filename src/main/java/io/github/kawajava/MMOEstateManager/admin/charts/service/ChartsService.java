package io.github.kawajava.MMOEstateManager.admin.charts.service;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.borough.repository.AdminBoroughRepository;
import io.github.kawajava.MMOEstateManager.admin.charts.model.ClanShareDto;
import io.github.kawajava.MMOEstateManager.admin.charts.model.GoldInBorough;
import io.github.kawajava.MMOEstateManager.admin.charts.model.PlayerBoroughCountDto;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.model.AdminGoldHistory;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.repository.AdminGoldHistoryRepository;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.player.repository.AdminPlayerRepository;
import io.github.kawajava.MMOEstateManager.common.model.Clan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChartsService {

    private final AdminGoldHistoryRepository adminGoldHistoryRepository;
    private final AdminBoroughRepository adminBoroughRepository;
    private final AdminPlayerRepository adminPlayerRepository;

    public List<GoldInBorough> getGoldAmountGroupedByDate(Long boroughId) {

        var historyList = adminGoldHistoryRepository.findByBoroughId(boroughId);
        return historyList.stream()
            .sorted(Comparator.comparing(AdminGoldHistory::getDateAdded))
            .map(history -> new GoldInBorough(history.getDateAdded(), history.getGold()))
            .collect(Collectors.toList());
    }

    public List<ClanShareDto> calculateClanSharesInCountry(Long countryId) {
        List<AdminBorough> boroughs = getBoroughsByCountry(countryId);
        Map<Long, AdminPlayer> playersById = getLeadersFromBoroughs(boroughs).stream()
                .collect(Collectors.toMap(AdminPlayer::getId, Function.identity()));

        Map<Clan, Long> clanCounts = boroughs.stream()
                .map(borough -> playersById.get(borough.getActualLeaderId()))
                .filter(Objects::nonNull)
                .filter(player -> player.getClan() != null)
                .map(AdminPlayer::getClan)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return mapClanCountsToDto(clanCounts, boroughs.size());
    }

    private List<AdminBorough> getBoroughsByCountry(Long countryId) {
        return adminBoroughRepository.findAllByCountryId(countryId);
    }

    private List<AdminPlayer> getLeadersFromBoroughs(List<AdminBorough> boroughs) {
        List<Long> leaderIds = boroughs.stream()
                .map(AdminBorough::getActualLeaderId)
                .filter(Objects::nonNull)
                .toList();
        return adminPlayerRepository.findAllById(leaderIds);
    }

    private Map<Clan, Long> countClans(List<AdminPlayer> players) {
        return players.stream()
                .filter(player -> player.getClan() != null)
                .collect(Collectors.groupingBy(AdminPlayer::getClan, Collectors.counting()));
    }

    private List<ClanShareDto> mapClanCountsToDto(Map<Clan, Long> clanCounts, int totalBoroughs) {
        return clanCounts.entrySet().stream()
                .map(entry -> {
                    String clanName = entry.getKey().name();
                    double percentage = (entry.getValue() * 100.0) / totalBoroughs;
                    return new ClanShareDto(clanName, percentage);
                })
                .toList();
    }

    public List<PlayerBoroughCountDto> calculatePlayerBoroughCountsInCountry(Long countryId) {
        List<AdminBorough> boroughs = getBoroughsByCountry(countryId);

        Map<Long, Long> leaderCounts = countLeaderBoroughs(boroughs);
        List<AdminPlayer> leaders = getLeadersByIds(leaderCounts.keySet());

        return mapLeaderCountsToDto(leaderCounts, leaders);
    }

    private Map<Long, Long> countLeaderBoroughs(List<AdminBorough> boroughs) {
        return boroughs.stream()
                .filter(borough -> borough.getActualLeaderId() != null)
                .collect(Collectors.groupingBy(AdminBorough::getActualLeaderId, Collectors.counting()));
    }

    private List<AdminPlayer> getLeadersByIds(Set<Long> leaderIds) {
        return adminPlayerRepository.findAllById(leaderIds);
    }

    private List<PlayerBoroughCountDto> mapLeaderCountsToDto(Map<Long, Long> leaderCounts, List<AdminPlayer> players) {
        Map<Long, String> playerNamesById = players.stream()
                .collect(Collectors.toMap(AdminPlayer::getId, AdminPlayer::getName));

        return leaderCounts.entrySet().stream()
                .map(entry -> {
                    String playerName = playerNamesById.get(entry.getKey());
                    Long count = entry.getValue();
                    return new PlayerBoroughCountDto(playerName, count);
                })
                .sorted(Comparator.comparing(PlayerBoroughCountDto::getBoroughCount).reversed())
                .toList();
    }

}
