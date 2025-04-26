package io.github.kawajava.MMOEstateManager.admin.charts.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.borough.repository.AdminBoroughRepository;
import io.github.kawajava.MMOEstateManager.admin.charts.model.ClanShareDto;
import io.github.kawajava.MMOEstateManager.admin.charts.model.PlayerBoroughCountDto;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.player.repository.AdminPlayerRepository;
import io.github.kawajava.MMOEstateManager.common.model.Clan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChartsServiceTest {

    @Mock
    private AdminBoroughRepository boroughRepository;

    @Mock
    private AdminPlayerRepository playerRepository;

    @InjectMocks
    private ChartsService chartsService;

    @Test
    void shouldCorrectlyCalculateClanSharesInCountry() {
        List<AdminBorough> boroughs = List.of(
                new AdminBorough(1L, "Borough1", "borough-1", 1L, 1L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(2L, "Borough2", "borough-2", 1L, 2L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(3L, "Borough3", "borough-3", 1L, 1L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(4L, "Borough4", "borough-4", 1L, 3L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(5L, "Borough5", "borough-5", 1L, 4L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false)
        );

        List<AdminPlayer> players = List.of(
                new AdminPlayer(1L, "Player1", "player1@example.com", Clan.Clan1, true, LocalDate.now(), "player1"),
                new AdminPlayer(2L, "Player2", "player2@example.com", Clan.Clan2, true, LocalDate.now(), "player2"),
                new AdminPlayer(3L, "Player3", "player3@example.com", Clan.Clan1, true, LocalDate.now(), "player3"),
                new AdminPlayer(4L, "Player4", "player4@example.com", Clan.Clan3, true, LocalDate.now(), "player4")
        );
        Long countryId = 1L;

        when(boroughRepository.findAllByCountryId(countryId)).thenReturn(boroughs);
        when(playerRepository.findAllById(anyList())).thenReturn(players);

        List<ClanShareDto> result = chartsService.calculateClanSharesInCountry(countryId);

        assertThat(result).hasSize(3);

        Map<String, Double> resultMap = result.stream()
                .collect(Collectors.toMap(ClanShareDto::getClanName, ClanShareDto::getPercentage));

        assertThat(resultMap.get("Clan1")).isCloseTo(60.0, within(0.01));
        assertThat(resultMap.get("Clan2")).isCloseTo(20.0, within(0.01));
        assertThat(resultMap.get("Clan3")).isCloseTo(20.0, within(0.01));
    }
    @Test
    void shouldCorrectlyHandleLeadersWithoutClanAndBoroughsWithoutLeader() {
        Long countryId = 1L;

        List<AdminBorough> testBoroughs = List.of(
                new AdminBorough(1L, "Borough1", "borough-1", 1L, 1L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(2L, "Borough2", "borough-2", 1L, 2L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(3L, "Borough3", "borough-3", 1L, null, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(4L, "Borough4", "borough-4", 1L, 3L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(5L, "Borough5", "borough-5", 1L, 3L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false)
        );

        List<AdminPlayer> testPlayers = List.of(
                new AdminPlayer(1L, "Player1", "player1@example.com", Clan.Clan1, true, LocalDate.now(), "player1"),
                new AdminPlayer(2L, "Player2", "player2@example.com", null, true, LocalDate.now(), "player2"), // brak klanu
                new AdminPlayer(3L, "Player3", "player3@example.com", Clan.Clan2, true, LocalDate.now(), "player3")
        );

        when(boroughRepository.findAllByCountryId(countryId)).thenReturn(testBoroughs);
        when(playerRepository.findAllById(anyList())).thenReturn(testPlayers);

        List<ClanShareDto> result = chartsService.calculateClanSharesInCountry(countryId);

        assertThat(result).hasSize(2);

        Map<String, Double> resultMap = result.stream()
                .collect(Collectors.toMap(ClanShareDto::getClanName, ClanShareDto::getPercentage));

        assertThat(resultMap.get("Clan1")).isCloseTo(20.0, within(0.01));
        assertThat(resultMap.get("Clan2")).isCloseTo(40.0, within(0.01));
    }
    @Test
    void shouldCorrectlyCalculatePlayerBoroughCountsInCountry() {
        Long countryId = 1L;

        List<AdminBorough> testBoroughs = List.of(
                new AdminBorough(1L, "Borough1", "borough-1", 1L, 1L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(2L, "Borough2", "borough-2", 1L, 2L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(3L, "Borough3", "borough-3", 1L, 1L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(4L, "Borough4", "borough-4", 1L, 3L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(5L, "Borough5", "borough-5", 1L, 3L, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false)
        );

        List<AdminPlayer> testPlayers = List.of(
                new AdminPlayer(1L, "Player1", "player1@example.com", Clan.Clan1, true, LocalDate.now(), "player1"),
                new AdminPlayer(2L, "Player2", "player2@example.com", Clan.Clan2, true, LocalDate.now(), "player2"),
                new AdminPlayer(3L, "Player3", "player3@example.com", Clan.Clan3, true, LocalDate.now(), "player3")
        );

        when(boroughRepository.findAllByCountryId(countryId)).thenReturn(testBoroughs);
        when(playerRepository.findAllById(anySet())).thenReturn(testPlayers);

        List<PlayerBoroughCountDto> result = chartsService.calculatePlayerBoroughCountsInCountry(countryId);

        assertThat(result).hasSize(3);

        Map<String, Long> resultMap = result.stream()
                .collect(Collectors.toMap(PlayerBoroughCountDto::getPlayerName, PlayerBoroughCountDto::getBoroughCount));

        assertThat(resultMap).containsEntry("Player1", 2L);
        assertThat(resultMap).containsEntry("Player3", 2L);
        assertThat(resultMap).containsEntry("Player2", 1L);
    }
    @Test
    void shouldHandleBoroughsWithoutLeaderId() {
        Long countryId = 1L;

        List<AdminBorough> testBoroughs = List.of(
                new AdminBorough(1L, "Borough1", "borough-1", 1L, null, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false),
                new AdminBorough(2L, "Borough2", "borough-2", 1L, null, LocalDateTime.now(), BigDecimal.ZERO, null, LocalDateTime.now(), false)
        );

        List<AdminPlayer> testPlayers = List.of();

        when(boroughRepository.findAllByCountryId(countryId)).thenReturn(testBoroughs);
        when(playerRepository.findAllById(anySet())).thenReturn(testPlayers);

        List<PlayerBoroughCountDto> result = chartsService.calculatePlayerBoroughCountsInCountry(countryId);

        assertThat(result).isEmpty();
    }
}
