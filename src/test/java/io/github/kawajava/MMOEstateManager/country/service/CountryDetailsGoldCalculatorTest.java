package io.github.kawajava.MMOEstateManager.country.service;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.model.Clan;
import io.github.kawajava.MMOEstateManager.country.model.Country;
import io.github.kawajava.MMOEstateManager.country.model.PlayerInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CountryDetailsGoldCalculatorTest {

    @InjectMocks
    private CountryDetailsGoldCalculator goldCalculator;

    @Test
    void shouldAddAllGoldInCountryCorrectly() {

        List<Borough> testBoroughs = createTestBoroughs();
        var allGoldInCountry = goldCalculator.getAllGoldInCountry(testBoroughs);

        assertThat(allGoldInCountry).isNotNull();
        assertThat(allGoldInCountry).isEqualTo(BigDecimal.valueOf(115000));
    }

    @Test
    void shouldCalculateGoldToCollectInCountryCorrectly() {
        Country country = Country.builder()
                .id(1L)
                .name("Country1")
                .slug("country-1")
                .actualSheriffId(1L)
                .goldLimit(BigDecimal.valueOf(10000))
                .sheriffStartDate(LocalDateTime.now())
                .build();

        List<Borough> testBoroughs = createTestBoroughs();
        var goldToCollect = goldCalculator.getGoldToCollect(testBoroughs, country);

        assertThat(goldToCollect).isNotNull();
        assertThat(goldToCollect).isEqualTo(BigDecimal.valueOf(80000));
    }

    @Test
    void shouldReturnZeroWhenLimitIsBiggerThanGolds() {
        Country country = Country.builder()
                .id(1L)
                .name("Country1")
                .slug("country-1")
                .actualSheriffId(1L)
                .goldLimit(BigDecimal.valueOf(100000))
                .sheriffStartDate(LocalDateTime.now())
                .build();

        List<Borough> testBoroughs = createTestBoroughs();
        var goldToCollect = goldCalculator.getGoldToCollect(testBoroughs, country);

        assertThat(goldToCollect).isNotNull();
        assertThat(goldToCollect).isEqualTo(BigDecimal.ZERO);
    }
    @Test
    void shouldGetGoldByPlayersCorrectly() {
        List<Borough> testBoroughs = createTestBoroughs();
        Map<String, BigDecimal> goldByPlayers = goldCalculator.getGoldByPlayers(testBoroughs, createPlayerInfoTestMap());

        assertThat(goldByPlayers).isNotNull();
        assertThat(goldByPlayers.size()).isEqualTo(3);
        assertThat(goldByPlayers.get("Player1")).isEqualTo(BigDecimal.valueOf(15000));
        assertThat(goldByPlayers.get("Player2")).isEqualTo(BigDecimal.valueOf(75000));
        assertThat(goldByPlayers.get("Player3")).isEqualTo(BigDecimal.valueOf(25000));
    }

    @Test
    void shouldGetGoldByClanCorrectly() {
        List<Borough> testBoroughs = createTestBoroughs();
        Map<Clan, BigDecimal> goldByClan = goldCalculator.getGoldByClan(testBoroughs, createPlayerInfoTestMap());

        assertThat(goldByClan).isNotNull();
        assertThat(goldByClan.size()).isEqualTo(2);
        assertThat(goldByClan.get(Clan.Clan1)).isEqualTo(BigDecimal.valueOf(15000));
        assertThat(goldByClan.get(Clan.Clan2)).isEqualTo(BigDecimal.valueOf(100000));
    }

    @Test
    void shouldCalculatePlayerPercentageCorrectly() {
        List<Borough> testBoroughs = createTestBoroughs();
        var allGoldInCountry = BigDecimal.valueOf(115000);

        Map<String, BigDecimal> playerPercentageMap = goldCalculator.calculatePlayerPercentage(testBoroughs, createPlayerInfoTestMap(), allGoldInCountry);
        BigDecimal totalPercentage = playerPercentageMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(playerPercentageMap).isNotNull();
        assertThat(playerPercentageMap.size()).isEqualTo(3);
        assertThat(playerPercentageMap.get("Player1")).isEqualTo(new BigDecimal("13.0400"));
        assertThat(playerPercentageMap.get("Player2")).isEqualTo(new BigDecimal("65.2200"));
        assertThat(playerPercentageMap.get("Player3")).isEqualTo(new BigDecimal("21.7400"));
        assertThat(totalPercentage.compareTo(BigDecimal.valueOf(100.0))).isEqualTo(0);
    }

    @Test
    void shouldCalculateClanPercentageCorrectly() {
        List<Borough> testBoroughs = createTestBoroughs();
        var allGoldInCountry = BigDecimal.valueOf(115000);

        Map<Clan, BigDecimal> clanPercentageMap = goldCalculator.calculateClanPercentage(testBoroughs, createPlayerInfoTestMap(), allGoldInCountry);
        BigDecimal totalPercentage = clanPercentageMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(clanPercentageMap).isNotNull();
        assertThat(clanPercentageMap.size()).isEqualTo(2);
        assertThat(clanPercentageMap.get(Clan.Clan1)).isEqualTo(new BigDecimal("13.0400"));
        assertThat(clanPercentageMap.get(Clan.Clan2)).isEqualTo(new BigDecimal("86.9600"));
        assertThat(totalPercentage.compareTo(BigDecimal.valueOf(100.0))).isEqualTo(0);
    }

    Map<Long, PlayerInfo> createPlayerInfoTestMap() {
        var player1 = PlayerInfo.builder()
                .id(5L)
                .name("Player1")
                .clan(Clan.Clan1)
                .build();
        var player2 = PlayerInfo.builder()
                .id(6L)
                .name("Player2")
                .clan(Clan.Clan2)
                .build();
        var player3 = PlayerInfo.builder()
                .id(7L)
                .name("Player3")
                .clan(Clan.Clan2)
                .build();
        return Map.of(5L, player1, 6L, player2, 7L, player3);
    }

    List<Borough> createTestBoroughs() {
        var borough1 = Borough.builder()
                .id(1L)
                .name("Borough1")
                .slug("borough-1")
                .countryId(1L)
                .actualLeader(null)
                .leaderStartDate(LocalDateTime.now())
                .actualGold(BigDecimal.valueOf(10000))
                .goldAddedBy(null)
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();
        var borough2 = Borough.builder()
                .id(2L)
                .name("Borough2")
                .slug("borough-2")
                .countryId(1L)
                .actualLeader(null)
                .leaderStartDate(LocalDateTime.now())
                .actualGold(BigDecimal.valueOf(75000))
                .goldAddedBy(null)
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();
        var borough3 = Borough.builder()
                .id(3L)
                .name("Borough3")
                .slug("borough-3")
                .countryId(1L)
                .actualLeader(null)
                .leaderStartDate(LocalDateTime.now())
                .actualGold(BigDecimal.valueOf(5000))
                .goldAddedBy(null)
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();
        var borough4 = Borough.builder()
                .id(4L)
                .name("Borough4")
                .slug("borough-4")
                .countryId(1L)
                .actualLeader(null)
                .leaderStartDate(LocalDateTime.now())
                .actualGold(BigDecimal.valueOf(25000))
                .goldAddedBy(null)
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();
        return List.of(borough1, borough2, borough3, borough4);
    }
}