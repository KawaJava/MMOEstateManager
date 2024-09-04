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

        BigDecimal allGoldInCountry = goldCalculator.getAllGoldInCountry(testBoroughs);

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

        BigDecimal goldToCollect = goldCalculator.getGoldToCollect(testBoroughs, country);

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

        BigDecimal goldToCollect = goldCalculator.getGoldToCollect(testBoroughs, country);

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
        BigDecimal allGoldInCountry = BigDecimal.valueOf(110000);

        Map<String, BigDecimal> playerPercentageMap = goldCalculator.calculatePlayerPercentage(testBoroughs, createPlayerInfoTestMap(), allGoldInCountry);

        assertThat(playerPercentageMap).isNotNull();
        assertThat(playerPercentageMap.size()).isEqualTo(3);

        assertThat(playerPercentageMap.get("Player1")).isEqualTo(new BigDecimal("13.6400"));
        assertThat(playerPercentageMap.get("Player2")).isEqualTo(new BigDecimal("68.1800"));
        assertThat(playerPercentageMap.get("Player3")).isEqualTo(new BigDecimal("22.7300"));
    }

    @Test
    void shouldCalculateClanPercentageCorrectly() {
        List<Borough> testBoroughs = createTestBoroughs();
        BigDecimal allGoldInCountry = BigDecimal.valueOf(110000);

        Map<Clan, BigDecimal> clanPercentageMap = goldCalculator.calculateClanPercentage(testBoroughs, createPlayerInfoTestMap(), allGoldInCountry);

        assertThat(clanPercentageMap).isNotNull();
        assertThat(clanPercentageMap.size()).isEqualTo(2);

        assertThat(clanPercentageMap.get(Clan.Clan1)).isEqualTo(new BigDecimal("13.6400"));
        assertThat(clanPercentageMap.get(Clan.Clan2)).isEqualTo(new BigDecimal("90.9100"));
    }



    Map<Long, PlayerInfo> createPlayerInfoTestMap() {
        PlayerInfo player1 = PlayerInfo.builder()
                .id(5L)
                .name("Player1")
                .clan(Clan.Clan1)
                .build();

        PlayerInfo player2 = PlayerInfo.builder()
                .id(6L)
                .name("Player2")
                .clan(Clan.Clan2)
                .build();
        PlayerInfo player3 = PlayerInfo.builder()
                .id(7L)
                .name("Player3")
                .clan(Clan.Clan2)
                .build();

        return Map.of(
                5L, player1,
                6L, player2,
                7L, player3
        );
    }

    List<Borough> createTestBoroughs() {
        Borough borough1 = Borough.builder()
                .id(1L)
                .name("Borough1")
                .slug("borough-1")
                .countryId(1L)
                .actualLeaderId(5L)
                .leaderStartDate(LocalDateTime.now())
                .actualGold(BigDecimal.valueOf(10000))
                .goldAddedBy(5L)
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();

        Borough borough2 = Borough.builder()
                .id(2L)
                .name("Borough2")
                .slug("borough-2")
                .countryId(1L)
                .actualLeaderId(6L)
                .leaderStartDate(LocalDateTime.now())
                .actualGold(BigDecimal.valueOf(75000))
                .goldAddedBy(6L)
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();

        Borough borough3 = Borough.builder()
                .id(3L)
                .name("Borough3")
                .slug("borough-3")
                .countryId(1L)
                .actualLeaderId(5L)
                .leaderStartDate(LocalDateTime.now())
                .actualGold(BigDecimal.valueOf(5000))
                .goldAddedBy(5L)
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();

        Borough borough4 = Borough.builder()
                .id(4L)
                .name("Borough4")
                .slug("borough-4")
                .countryId(1L)
                .actualLeaderId(7L)
                .leaderStartDate(LocalDateTime.now())
                .actualGold(BigDecimal.valueOf(25000))
                .goldAddedBy(7L)
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();

        return List.of(borough1, borough2, borough3, borough4);
    }
}