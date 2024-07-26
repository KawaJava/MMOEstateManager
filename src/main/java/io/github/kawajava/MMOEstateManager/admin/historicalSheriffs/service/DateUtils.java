package io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtils {
    public static LocalDateTime asStartOfDay(LocalDate date) {
        return date == null ? null : date.atStartOfDay();
    }
    public static LocalDateTime atEndOfDay(LocalDate date) {
        return date == null ? null : date.atTime(LocalTime.MAX);
    }
}
