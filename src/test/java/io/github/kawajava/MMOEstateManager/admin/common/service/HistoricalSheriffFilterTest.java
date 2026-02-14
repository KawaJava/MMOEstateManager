package io.github.kawajava.MMOEstateManager.admin.common.service;

import io.github.kawajava.MMOEstateManager.admin.historicalSheriff.model.AdminHistoricalSheriff;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriff.service.dto.HistoricalSheriffFilteredDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.github.kawajava.MMOEstateManager.admin.common.service.HistoricalSheriffFilterService.filterHistoricalSheriffs;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HistoricalSheriffFilterTest {

    @InjectMocks
    HistoricalSheriffFilterService service;

    private List<AdminHistoricalSheriff> allSheriffs;

    @BeforeEach
    public void setUp() {
        prepareTestData();
    }

    @Test
    public void shouldFilterByCountryIdCorrectly() {
        var filter = new HistoricalSheriffFilteredDto(1L, null, null, null);
        var result = filterHistoricalSheriffs(filter, allSheriffs, null, null);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(1L, 3L);
        assertThat(result).extracting("countryId").containsOnly(1L);
    }

    @Test
    public void shouldFilterByPlayerIdCorrectly() {
        var filter = new HistoricalSheriffFilteredDto(null, 2L, null, null);
        var result = filterHistoricalSheriffs(filter, allSheriffs, null, null);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPlayerId()).isEqualTo(2L);
        assertThat(result.get(0).getId()).isEqualTo(2L);
    }

    @Test
    public void shouldFilterByStartDateCorrectly() {
        var startDateTime = LocalDateTime.of(2022, 1, 5, 0, 0);
        var filter = new HistoricalSheriffFilteredDto(null, null, LocalDate.of(2022, 1, 5), null);
        var result = filterHistoricalSheriffs(filter, allSheriffs, startDateTime, null);
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
        var filter = new HistoricalSheriffFilteredDto(null, null, null, LocalDate.of(2022, 1, 15));
        var result = filterHistoricalSheriffs(filter, allSheriffs, null, endDateTime);
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
        var filter = new HistoricalSheriffFilteredDto(1L, null, LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 18));
        var result = filterHistoricalSheriffs(filter, allSheriffs, startDateTime, endDateTime);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(1L, 3L);
        assertThat(result).extracting("countryId").containsOnly(1L);
        assertThat(result).extracting("startDate").containsExactly(LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 20, 0, 0));
        assertThat(result).extracting("endDate").containsExactly(LocalDateTime.of(2022, 1, 10, 0, 0),
                LocalDateTime.of(2022, 1, 25, 0, 0));

    }

    private void prepareTestData() {
        allSheriffs = Arrays.asList(
                AdminHistoricalSheriff.builder()
                        .id(1L)
                        .countryId(1L)
                        .playerId(1L)
                        .startDate(LocalDateTime.of(2022, 1, 1, 0, 0))
                        .endDate(LocalDateTime.of(2022, 1, 10, 0, 0))
                        .build(),
                AdminHistoricalSheriff.builder()
                        .id(2L)
                        .countryId(2L)
                        .playerId(2L)
                        .startDate(LocalDateTime.of(2022, 1, 5, 0, 0))
                        .endDate(LocalDateTime.of(2022, 1, 15, 0, 0))
                        .build(),
                AdminHistoricalSheriff.builder()
                        .id(3L)
                        .countryId(1L)
                        .playerId(3L)
                        .startDate(LocalDateTime.of(2022, 1, 20, 0, 0))
                        .endDate(LocalDateTime.of(2022, 1, 25, 0, 0))
                        .build()
        );
    }

}