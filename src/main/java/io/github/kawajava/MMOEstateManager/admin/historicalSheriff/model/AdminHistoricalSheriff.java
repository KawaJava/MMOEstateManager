package io.github.kawajava.MMOEstateManager.admin.historicalSheriff.model;

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
public class AdminHistoricalSheriff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long countryId;
    private Long playerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
