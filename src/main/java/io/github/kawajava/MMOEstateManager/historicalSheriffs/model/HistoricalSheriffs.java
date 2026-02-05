package io.github.kawajava.MMOEstateManager.historicalSheriffs.model;

import io.github.kawajava.MMOEstateManager.player.model.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historical_sheriff")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalSheriffs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long countryId;
    @ManyToOne
    @JoinColumn(name = "playerId")
    private Player player;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
