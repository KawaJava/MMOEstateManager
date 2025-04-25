package io.github.kawajava.MMOEstateManager.admin.player.service;

import io.github.kawajava.MMOEstateManager.admin.player.controller.dto.AdminPlayerToAutocomplete;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.player.repository.AdminPlayerRepository;
import io.github.kawajava.MMOEstateManager.common.model.Clan;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AdminPlayerServiceTest {

    @Mock
    private AdminPlayerRepository adminPlayerRepository;

    @InjectMocks
    private AdminPlayerService adminPlayerService;

    private static List<AdminPlayer> players;

    @BeforeAll
    public static void setUp() {
        players = List.of(
                new AdminPlayer(1L, "Player1", "player1@example.com", Clan.Clan1, true, LocalDate.now(), "player-1"),
                new AdminPlayer(2L, "ProPlayer", "proplayer@example.com", Clan.Clan2, true, LocalDate.now(), "proplayer-slug"),
                new AdminPlayer(3L, "Gamer", "gamer@example.com", Clan.Clan3, true, LocalDate.now(), "gamer-slug"),
                new AdminPlayer(4L, "Champion", "champion@example.com", Clan.Clan4, true, LocalDate.now(), "champion-slug"),
                new AdminPlayer(5L, "Noob", "noob@example.com", Clan.Clan1, true, LocalDate.now(), "noob-slug"),
                new AdminPlayer(6L, "ElitePlayer", "eliteplayer@example.com", Clan.Clan2, true, LocalDate.now(), "eliteplayer-slug"),
                new AdminPlayer(7L, "Master", "master@example.com", Clan.Clan3, true, LocalDate.now(), "master-slug"),
                new AdminPlayer(8L, "Prodigy", "prodigy@example.com", Clan.Clan4, true, LocalDate.now(), "prodigy-slug"),
                new AdminPlayer(9L, "Ace", "ace@example.com", Clan.Clan1, true, LocalDate.now(), "ace-slug"),
                new AdminPlayer(10L, "MVP", "mvp@example.com", Clan.Clan2, true, LocalDate.now(), "mvp-slug"),
                new AdminPlayer(11L, "Player2", "player2@example.com", Clan.Clan1, true, LocalDate.now(), "player-2"),
                new AdminPlayer(12L, "Player3", "player3@example.com", Clan.Clan2, true, LocalDate.now(), "player-3"),
                new AdminPlayer(13L, "Player4", "player4@example.com", Clan.Clan3, true, LocalDate.now(), "player-4"),
                new AdminPlayer(14L, "Player5", "player5@example.com", Clan.Clan4, true, LocalDate.now(), "player-5"),
                new AdminPlayer(15L, "Player6", "player6@example.com", Clan.Clan1, true, LocalDate.now(), "player-6")
        );
    }

    @Test
    public void shouldReturnAdminPlayersToAutocompleteCorrectly() {
        when(adminPlayerRepository.findAll()).thenReturn(players);

        List<AdminPlayerToAutocomplete> result = adminPlayerService.getAdminPlayersToAutocomplete("Pr");

        assertThat(result)
                .hasSize(2)
                .extracting(AdminPlayerToAutocomplete::name)
                .containsExactly("ProPlayer","Prodigy");
    }

    @Test
    public void shouldReturnMaxFiveResults() {
        when(adminPlayerRepository.findAll()).thenReturn(players);

        List<AdminPlayerToAutocomplete> result = adminPlayerService.getAdminPlayersToAutocomplete("Player");

        assertThat(result)
                .hasSize(5)
                .extracting(AdminPlayerToAutocomplete::name)
                .containsExactly("Player1", "Player2", "Player3", "Player4", "Player5");
    }

    @Test
    public void shouldReturnEmptyListWhenNoPlayersMatchPrefix() {
        when(adminPlayerRepository.findAll()).thenReturn(players);

        List<AdminPlayerToAutocomplete> result = adminPlayerService.getAdminPlayersToAutocomplete("Z");

        assertThat(result).isEmpty();
    }
}