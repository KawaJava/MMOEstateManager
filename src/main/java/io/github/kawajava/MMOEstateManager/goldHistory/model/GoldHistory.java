package io.github.kawajava.MMOEstateManager.goldHistory.model;

import io.github.kawajava.MMOEstateManager.player.model.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoldHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long boroughId;
    private BigDecimal gold;
    @ManyToOne
    @JoinColumn(name = "goldAddedBy")
    private Player goldAddedBy;
    private LocalDateTime dateAdded;
    private Boolean emailSend;
}
