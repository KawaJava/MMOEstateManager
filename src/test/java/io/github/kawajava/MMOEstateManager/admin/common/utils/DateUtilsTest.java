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
        //given
        var date = LocalDate.of(2024, 7,26);
        var expected = LocalDateTime.of(2024,7,26,0,0, 0, 0);
        //when
        LocalDateTime newDate = DateUtils.asStartOfDay(date);
        //then
        assertThat(newDate).isNotNull();
        assertThat(newDate).isEqualTo(expected);
    }

    @Test
    void shouldReturnNullWhenStartDateIsNull() {
        //given
        LocalDate date = null;
        //when
        LocalDateTime newDate = DateUtils.asStartOfDay(date);
        //then
        assertThat(newDate).isNull();;
    }

    @Test
    void shouldFormatEndDateCorrectly() {
        //given
        var date = LocalDate.of(2024, 7,26);
        var before = LocalDateTime.of(2024,7,26,23,59, 59);
        var after = LocalDateTime.of(2024,7,27,0,0, 0);
        String expectedDateTime ="2024-07-26T23:59:59.999999999";
        //when
        LocalDateTime newDate = DateUtils.atEndOfDay(date);
        //then
        assertThat(newDate).isNotNull();
        assertThat(newDate).isAfter(before);
        assertThat(newDate).isBefore(after);
        assertThat(newDate).isEqualTo(expectedDateTime);
    }

    @Test
    void shouldReturnNullWhenEndDateIsNull() {
        //given
        LocalDate date = null;
        //when
        LocalDateTime newDate = DateUtils.atEndOfDay(date);
        //then
        assertThat(newDate).isNull();;
    }

}