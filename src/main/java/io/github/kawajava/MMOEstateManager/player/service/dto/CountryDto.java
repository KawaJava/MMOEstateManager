package io.github.kawajava.MMOEstateManager.player.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CountryDto {
    private Long id;
    private String name;
    private String slug;
    private BigDecimal goldLimit;
    private LocalDateTime sheriffStartDate;
}
