package io.github.kawajava.MMOEstateManager.admin.charts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerBoroughCountDto {
    private String playerName;
    private Long boroughCount;
}
