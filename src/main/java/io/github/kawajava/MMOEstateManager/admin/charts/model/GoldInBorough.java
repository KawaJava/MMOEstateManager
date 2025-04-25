package io.github.kawajava.MMOEstateManager.admin.charts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GoldInBorough {
    private LocalDateTime date;
    private BigDecimal amount;
}
