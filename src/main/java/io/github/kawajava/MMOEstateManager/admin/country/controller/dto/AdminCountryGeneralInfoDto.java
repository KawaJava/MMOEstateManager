package io.github.kawajava.MMOEstateManager.admin.country.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class AdminCountryGeneralInfoDto {

    @NotBlank
    @Length(min = 3)
    private String name;
    @NotBlank
    @Length(min = 3)
    private String slug;

}
