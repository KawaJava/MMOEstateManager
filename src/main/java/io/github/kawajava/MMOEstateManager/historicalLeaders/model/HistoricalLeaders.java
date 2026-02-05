package io.github.kawajava.MMOEstateManager.historicalLeaders.model;

import io.github.kawajava.MMOEstateManager.player.model.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historical_leader")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalLeaders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long boroughId;
    @ManyToOne
    @JoinColumn(name = "playerId")
    private Player player;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
