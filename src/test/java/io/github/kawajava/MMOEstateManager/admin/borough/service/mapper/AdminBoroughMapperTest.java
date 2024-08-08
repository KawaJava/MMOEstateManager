package io.github.kawajava.MMOEstateManager.admin.borough.service.mapper;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.github.kawajava.MMOEstateManager.admin.borough.service.mapper.AdminBoroughMapper.mapAdminBorough;
import static io.github.kawajava.MMOEstateManager.admin.borough.service.mapper.AdminBoroughMapper.mapAdminHistoricalLeaders;

import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AdminBoroughMapperTest {

    @Test
    public void shouldMapAdminBoroughCorrectly() {
        Long boroughId = 1L;
        Long leaderId = 2L;
        LocalDateTime now = LocalDateTime.now();
        AdminBorough adminBorough = AdminBorough.builder()
                .id(boroughId)
                .name("Test Borough")
                .slug("test-borough")
                .actualGold(BigDecimal.valueOf(100))
                .countryId(3L)
                .goldAddedBy(1L)
                .leaderStartDate(LocalDateTime.of(2022, 1, 1, 0, 0))
                .dateAdded(LocalDateTime.of(2022, 1, 1, 0, 0))
                .emailSend(true)
                .build();

        AdminBorough result = mapAdminBorough(boroughId, leaderId, adminBorough, now);

        assertThat(result.getId()).isEqualTo(boroughId);
        assertThat(result.getName()).isEqualTo("Test Borough");
        assertThat(result.getSlug()).isEqualTo("test-borough");
        assertThat(BigDecimal.valueOf(100)).isEqualTo(adminBorough.getActualGold());
        assertThat(result.getCountryId()).isEqualTo(3L);
        assertThat(result.getGoldAddedBy()).isEqualTo(1L);
        assertThat(result.getActualLeaderId()).isEqualTo(leaderId);
        assertThat(result.getLeaderStartDate()).isEqualTo(now);
        assertThat(result.getDateAdded()).isEqualTo(LocalDateTime.of(2022, 1, 1, 0, 0));
        assertThat(result.getEmailSend()).isTrue();
    }

    @Test
    public void shouldMapAdminHistoricalLeadersCorrectly() {
        Long boroughId = 1L;
        Long actualLeaderId = 2L;
        LocalDateTime oldStartDate = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime now = LocalDateTime.of(2022, 2, 1, 0, 0);

        AdminHistoricalLeaders result = mapAdminHistoricalLeaders(boroughId, actualLeaderId, oldStartDate, now);

        assertThat(result.getId()).isNull();
        assertThat(result.getBoroughId()).isEqualTo(boroughId);
        assertThat(result.getPlayerId()).isEqualTo(actualLeaderId);
        assertThat(result.getStartDate()).isEqualTo(oldStartDate);
        assertThat(result.getEndDate()).isEqualTo(now);
    }
}