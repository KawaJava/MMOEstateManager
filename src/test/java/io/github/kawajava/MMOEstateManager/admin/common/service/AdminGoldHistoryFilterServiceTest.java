package io.github.kawajava.MMOEstateManager.admin.common.service;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.controller.dto.GoldHistoryFilteredDto;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.model.AdminGoldHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AdminGoldHistoryFilterServiceTest {
    @InjectMocks
    AdminGoldHistoryFilterService service;

    private List<AdminGoldHistory> allGoldHistory;

    @BeforeEach
    public void setUp() {
        prepareTestData();
    }

    @Test
    public void shouldFilterByBoroughIdCorrectly() {
        var filter = new GoldHistoryFilteredDto(1L, null, null, null, null);
        var result = AdminGoldHistoryFilterService.filterGoldHistory(allGoldHistory, filter, null, null);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(1L, 3L);
        assertThat(result).extracting("boroughId").containsOnly(1L);
    }

    @Test
    public void shouldFilterByGoldAddedByCorrectly() {
        var filter = new GoldHistoryFilteredDto(null, 123L, null, null, null);
        var result = AdminGoldHistoryFilterService.filterGoldHistory(allGoldHistory, filter, null, null);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGoldAddedBy()).isEqualTo(123L);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    public void shouldFilterByEmailSendCorrectly() {
        var filter = new GoldHistoryFilteredDto(null, null, true, null, null);
        var result = AdminGoldHistoryFilterService.filterGoldHistory(allGoldHistory, filter, null, null);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(2L, 3L);
        assertThat(result).extracting("emailSend").containsOnly(true);
    }

    @Test
    public void shouldFilterByStartDateCorrectly() {
        var startDateTime = LocalDateTime.of(2022, 1, 5, 0, 0);
        var filter = new GoldHistoryFilteredDto(null, null, null, LocalDate.of(2022, 1, 5), null);
        var result = AdminGoldHistoryFilterService.filterGoldHistory(allGoldHistory, filter, startDateTime, null);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(2L, 3L);
        assertThat(result).extracting("dateAdded").containsExactly(
                LocalDateTime.of(2022, 1, 5, 0, 0),
                LocalDateTime.of(2022, 1, 20, 0, 0)
        );
    }

    @Test
    public void shouldFilterByEndDateCorrectly() {
        var endDateTime = LocalDateTime.of(2022, 1, 15, 0, 0);
        var filter = new GoldHistoryFilteredDto(null, null, null, null, LocalDate.of(2022, 1, 15));
        var result = AdminGoldHistoryFilterService.filterGoldHistory(allGoldHistory, filter, null, endDateTime);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(1L, 2L);
        assertThat(result).extracting("dateAdded").containsExactly(
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 5, 0, 0)
        );
    }

    @Test
    public void shouldFilterByAllCriteriaCorrectly() {
        var startDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        var endDateTime = LocalDateTime.of(2022, 1, 25, 0, 0);
        var filter = new GoldHistoryFilteredDto(1L, null, null, LocalDate.of(2022, 1, 3), LocalDate.of(2022, 1, 18));
        var result = AdminGoldHistoryFilterService.filterGoldHistory(allGoldHistory, filter, startDateTime, endDateTime);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("id").containsExactly(1L, 3L);
        assertThat(result).extracting("boroughId").containsOnly(1L);
        assertThat(result).extracting("dateAdded").containsExactly(LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 20, 0, 0));
    }

    private void prepareTestData() {
        allGoldHistory = Arrays.asList(
                AdminGoldHistory.builder()
                        .id(1L)
                        .boroughId(1L)
                        .goldAddedBy(123L)
                        .emailSend(false)
                        .dateAdded(LocalDateTime.of(2022, 1, 1, 0, 0))
                        .build(),
                AdminGoldHistory.builder()
                        .id(2L)
                        .boroughId(2L)
                        .goldAddedBy(124L)
                        .emailSend(true)
                        .dateAdded(LocalDateTime.of(2022, 1, 5, 0, 0))
                        .build(),
                AdminGoldHistory.builder()
                        .id(3L)
                        .boroughId(1L)
                        .goldAddedBy(125L)
                        .emailSend(true)
                        .dateAdded(LocalDateTime.of(2022, 1, 20, 0, 0))
                        .build()
        );
    }
}