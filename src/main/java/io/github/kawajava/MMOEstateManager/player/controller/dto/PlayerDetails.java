package io.github.kawajava.MMOEstateManager.player.controller.dto;

import io.github.kawajava.MMOEstateManager.common.model.Clan;
import io.github.kawajava.MMOEstateManager.country.model.Country;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PlayerDetails {
    private String name;
    private String email;
    private Clan clan;
    private boolean isActive;
    private LocalDate created;
    private List<Country> countries;
}
