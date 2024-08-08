package io.github.kawajava.MMOEstateManager.admin.borough.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class AdminBoroughGeneralInfoDto {
    @NotBlank
    @Length(min = 3)
    private String name;
    @NotBlank
    @Length(min = 3)
    private String slug;
    @NotNull
    private Long countryId;
}
