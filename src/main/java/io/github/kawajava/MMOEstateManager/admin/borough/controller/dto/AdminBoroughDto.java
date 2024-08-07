package io.github.kawajava.MMOEstateManager.admin.borough.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AdminBoroughDto {
    @NotBlank
    @Length(min = 3)
    private String name;
    @NotBlank
    @Length(min = 3)
    private String slug;
    @NotNull
    private Long countryId;
    @NotNull
    private Long actualLeaderId;
    @NotNull
    @DateTimeFormat
    private LocalDateTime leaderStartDate;
    @Min(1000)
    private BigDecimal actualGold;
    @NotNull
    private Long goldAddedBy;
}
