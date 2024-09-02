package io.github.kawajava.MMOEstateManager.admin.player.controller.dto;

import io.github.kawajava.MMOEstateManager.common.model.Clan;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class AdminPlayerDto {
    @NotBlank
    @Length(min = 5)
    private String name;
    @NotNull
    @Email
    private String email;
    private Clan clan;
    @NotBlank
    @Length(min = 5)
    private String slug;
}
