package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.historicalLeader.model.AdminHistoricalLeader;
import io.github.kawajava.MMOEstateManager.admin.historicalLeader.service.dto.HistoricalLeaderFilteredDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import static io.github.kawajava.MMOEstateManager.admin.common.service.HistoricalLeadersFilterService.filterHistoricalLeaders;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HistoricalLeaderFilterServiceTest {

    @InjectMocks
    HistoricalLeadersFilterService service;

    private List<AdminHistoricalLeader> allLeaders;

    @BeforeEach
    public void setUp() {
        prepareTestData();
    }

    @Test
    public void shouldFilterByBoroughIdCorrectly() {
        var filter = new HistoricalLeaderFilteredDto(1L, null, null, null);
        var result = filterHistoricalLeaders(filter, allLeaders, null, null);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(1L, 3L);
        assertThat(result).extracting("boroughId").containsOnly(1L);
    }

    @Test
    public void shouldFilterByPlayerIdCorrectly() {
        var filter = new HistoricalLeaderFilteredDto(null, 2L, null, null);
        var result = filterHistoricalLeaders(filter, allLeaders, null, null);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPlayerId()).isEqualTo(2L);
        assertThat(result.get(0).getId()).isEqualTo(2L);
    }

    @Test
    public void shouldFilterByStartDateCorrectly() {
        var startDateTime = LocalDateTime.of(2022, 1, 5, 0, 0);
        var filter = new HistoricalLeaderFilteredDto(null, null, LocalDate.of(2022, 1, 5), null);
        var result = filterHistoricalLeaders(filter, allLeaders, startDateTime, null);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(2L, 3L);
        assertThat(result).extracting("startDate").containsExactly(
                LocalDateTime.of(2022, 1, 5, 0, 0),
                LocalDateTime.of(2022, 1, 20, 0, 0)
        );
    }

    @Test
    public void shouldFilterByEndDateCorrectly() {
        var endDateTime = LocalDateTime.of(2022, 1, 15, 0, 0);
        var filter = new HistoricalLeaderFilteredDto(null, null, null, LocalDate.of(2022, 1, 15));
        var result = filterHistoricalLeaders(filter, allLeaders, null, endDateTime);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(1L, 2L);
        assertThat(result).extracting("endDate").containsExactly(
                LocalDateTime.of(2022, 1, 10, 0, 0),
                LocalDateTime.of(2022, 1, 15, 0, 0)
        );
    }

    @Test
    public void shouldFilterByAllCriteriaCorrectly() {
        var startDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        var endDateTime = LocalDateTime.of(2022, 1, 25, 0, 0);
        var filter = new HistoricalLeaderFilteredDto(1L, null, LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 18));
        var result = filterHistoricalLeaders(filter, allLeaders, startDateTime, endDateTime);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(1L, 3L);
        assertThat(result).extracting("boroughId").containsOnly(1L);
        assertThat(result).extracting("startDate").containsExactly(LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 20, 0, 0));
        assertThat(result).extracting("endDate").containsExactly(LocalDateTime.of(2022, 1, 10, 0, 0),
                LocalDateTime.of(2022, 1, 25, 0, 0));
    }

    private void prepareTestData() {
        allLeaders = Arrays.asList(
                AdminHistoricalLeader.builder()
                        .id(1L)
                        .boroughId(1L)
                        .playerId(1L)
                        .startDate(LocalDateTime.of(2022, 1, 1, 0, 0))
                        .endDate(LocalDateTime.of(2022, 1, 10, 0, 0))
                        .build(),
                AdminHistoricalLeader.builder()
                        .id(2L)
                        .boroughId(2L)
                        .playerId(2L)
                        .startDate(LocalDateTime.of(2022, 1, 5, 0, 0))
                        .endDate(LocalDateTime.of(2022, 1, 15, 0, 0))
                        .build(),
                AdminHistoricalLeader.builder()
                        .id(3L)
                        .boroughId(1L)
                        .playerId(3L)
                        .startDate(LocalDateTime.of(2022, 1, 20, 0, 0))
                        .endDate(LocalDateTime.of(2022, 1, 25, 0, 0))
                        .build()
        );
    }
}