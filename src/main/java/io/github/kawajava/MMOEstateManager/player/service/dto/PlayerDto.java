package io.github.kawajava.MMOEstateManager.player.service.dto;

import io.github.kawajava.MMOEstateManager.common.model.Clan;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PlayerDto {
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Clan clan;
    private LocalDate created;
    private String slug;
    private List<CountryDto> countryDtos;
}
