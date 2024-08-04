package io.github.kawajava.MMOEstateManager.admin.common.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DateUtilsTest {

    @Test
    void shouldFormatStartDateCorrectly() {
        var date = LocalDate.of(2024, 7,26);
        var expected = LocalDateTime.of(2024,7,26,0,0, 0, 0);
        LocalDateTime newDate = DateUtils.asStartOfDay(date);
        assertThat(newDate).isNotNull();
        assertThat(newDate).isEqualTo(expected);
    }

    @Test
    void shouldReturnNullWhenStartDateIsNull() {
        LocalDate date = null;
        LocalDateTime newDate = DateUtils.asStartOfDay(date);
        assertThat(newDate).isNull();;
    }

    @Test
    void shouldFormatEndDateCorrectly() {
        var date = LocalDate.of(2024, 7,26);
        var before = LocalDateTime.of(2024,7,26,23,59, 59);
        var after = LocalDateTime.of(2024,7,27,0,0, 0);
        String expectedDateTime ="2024-07-26T23:59:59.999999999";
        LocalDateTime newDate = DateUtils.atEndOfDay(date);
        assertThat(newDate).isNotNull();
        assertThat(newDate).isAfter(before);
        assertThat(newDate).isBefore(after);
        assertThat(newDate).isEqualTo(expectedDateTime);
    }

    @Test
    void shouldReturnNullWhenEndDateIsNull() {
        LocalDate date = null;
        LocalDateTime newDate = DateUtils.atEndOfDay(date);
        assertThat(newDate).isNull();;
    }
}