package io.github.kawajava.MMOEstateManager.admin.goldHistory.controller.dto;

import java.time.LocalDate;

public record GoldHistoryFilteredDto(Long boroughId, Long goldAddedBy, Boolean emailSend, LocalDate startDate, LocalDate endDate) {
}
