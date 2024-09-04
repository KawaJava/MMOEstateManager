package io.github.kawajava.MMOEstateManager.borough.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GoldDto {
    @Min(0)
    private BigDecimal newGold;
    @NotNull
    private Long goldAddedBy;
}
