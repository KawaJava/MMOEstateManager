package io.github.kawajava.MMOEstateManager.admin.borough.service.mapper;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;

import java.time.LocalDateTime;

public class AdminBoroughMapper {
    public static AdminBorough mapAdminBorough(Long boroughId, Long leaderId,
                                                AdminBorough adminBorough, LocalDateTime now) {
        return AdminBorough.builder()
                .id(boroughId)
                .name(adminBorough.getName())
                .slug(adminBorough.getSlug())
                .actualGold(adminBorough.getActualGold())
                .countryId(adminBorough.getCountryId())
                .goldAddedBy(adminBorough.getGoldAddedBy())
                .actualLeaderId(leaderId)
                .leaderStartDate(now)
                .dateAdded(adminBorough.getDateAdded())
                .emailSend(adminBorough.getEmailSend())
                .build();
    }

    public static AdminHistoricalLeaders mapAdminHistoricalLeaders(
            Long boroughId, Long actualLeaderId, LocalDateTime oldSheriffStartDate, LocalDateTime now) {
        return AdminHistoricalLeaders.builder()
                .id(null)
                .boroughId(boroughId)
                .playerId(actualLeaderId)
                .startDate(oldSheriffStartDate)
                .endDate(now)
                .build();
    }
}
