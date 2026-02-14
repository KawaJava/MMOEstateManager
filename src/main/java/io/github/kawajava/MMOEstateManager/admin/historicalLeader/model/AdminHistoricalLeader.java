package io.github.kawajava.MMOEstateManager.admin.historicalLeader.model;

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
public class AdminHistoricalLeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long boroughId;
    private Long playerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
