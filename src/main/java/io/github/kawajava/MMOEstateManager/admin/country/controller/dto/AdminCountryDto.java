package io.github.kawajava.MMOEstateManager.admin.country.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AdminCountryDto {
    @NotBlank
    @Length(min = 3)
    private String name;
    @NotNull
    private Long actualSheriffId;
    @Min(10000)
    private BigDecimal goldLimit;
    @NotBlank
    @Length(min = 3)
    private String slug;
}
