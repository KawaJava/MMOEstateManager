package io.github.kawajava.MMOEstateManager.player.service;

import io.github.kawajava.MMOEstateManager.admin.player.model.Clan;
import io.github.kawajava.MMOEstateManager.country.model.Country;
import io.github.kawajava.MMOEstateManager.country.repository.CountryRepository;
import io.github.kawajava.MMOEstateManager.player.model.Player;
import io.github.kawajava.MMOEstateManager.player.repository.PlayerRepository;
import io.github.kawajava.MMOEstateManager.player.service.dto.CountryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player testPlayer;
    private Country testCountry1;
    private Country testCountry2;

    @BeforeEach
    void setUp() {
        prepareTestData();
    }

    @Test
    void shouldGetPlayerAndMapCorrectly() {

        when(playerRepository.findBySlug("test-user")).thenReturn(Optional.of(testPlayer));
        when(countryRepository.findAllByActualSheriffId(1L)).thenReturn(List.of(testCountry1, testCountry2));

        var playerDto = playerService.getPlayerBySlug("test-user");

        assertThat(playerDto.getId()).isEqualTo(testPlayer.getId());
        assertThat(playerDto.getName()).isEqualTo(testPlayer.getName());
        assertThat(playerDto.getEmail()).isEqualTo(testPlayer.getEmail());
        assertThat(playerDto.getSlug()).isEqualTo(testPlayer.getSlug());
        assertThat(playerDto.getClan()).isEqualTo(testPlayer.getClan());
        assertThat(playerDto.getCreated()).isEqualTo(testPlayer.getCreated());

        assertThat(playerDto.getCountryDtos()).hasSize(2);
        CountryDto countryDto1 = playerDto.getCountryDtos().get(0);
        CountryDto countryDto2 = playerDto.getCountryDtos().get(1);

        assertThat(countryDto1.getId()).isEqualTo(testCountry1.getId());
        assertThat(countryDto1.getName()).isEqualTo(testCountry1.getName());
        assertThat(countryDto1.getSlug()).isEqualTo(testCountry1.getSlug());
        assertThat(countryDto1.getGoldLimit()).isEqualTo(testCountry1.getGoldLimit());
        assertThat(countryDto1.getSheriffStartDate()).isEqualTo(testCountry1.getSheriffStartDate());

        assertThat(countryDto2.getId()).isEqualTo(testCountry2.getId());
        assertThat(countryDto2.getName()).isEqualTo(testCountry2.getName());
        assertThat(countryDto2.getSlug()).isEqualTo(testCountry2.getSlug());
        assertThat(countryDto2.getGoldLimit()).isEqualTo(testCountry2.getGoldLimit());
        assertThat(countryDto2.getSheriffStartDate()).isEqualTo(testCountry2.getSheriffStartDate());
    }

    void prepareTestData() {
        testPlayer = Player.builder()
                .id(1L)
                .name("Test User")
                .email("test.user@example.com")
                .clan(Clan.Clan1)
                .created(LocalDate.now())
                .slug("test-user")
                .isActive(true)
                .build();

        testCountry1 = Country.builder()
                .id(1L)
                .name("Country-1")
                .slug("country-1")
                .actualSheriffId(testPlayer.getId())
                .goldLimit(BigDecimal.valueOf(10000))
                .sheriffStartDate(LocalDateTime.now().minusDays(10))
                .build();

        testCountry2 = Country.builder()
                .id(2L)
                .name("Country-2")
                .slug("country-2")
                .actualSheriffId(testPlayer.getId())
                .goldLimit(BigDecimal.valueOf(10000))
                .sheriffStartDate(LocalDateTime.now().minusDays(10))
                .build();
    }
}
