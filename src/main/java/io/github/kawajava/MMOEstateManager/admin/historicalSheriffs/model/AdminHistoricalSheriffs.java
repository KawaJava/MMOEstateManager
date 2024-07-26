package io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model;

import io.github.kawajava.MMOEstateManager.admin.player.model.Clan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "historical_sheriffs")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminHistoricalSheriffs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long countryId;
    private Long playerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
