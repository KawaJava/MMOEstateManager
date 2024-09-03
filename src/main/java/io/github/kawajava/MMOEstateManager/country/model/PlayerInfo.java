package io.github.kawajava.MMOEstateManager.country.model;

import io.github.kawajava.MMOEstateManager.common.model.Clan;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PlayerInfo {
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Clan clan;
}
