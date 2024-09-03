package io.github.kawajava.MMOEstateManager.player.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoroughDto {
    private Long id;
    private String name;
    private String slug;
    private Long countryId;
    private LocalDateTime leaderStartDate;
}
