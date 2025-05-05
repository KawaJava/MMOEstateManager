package io.github.kawajava.MMOEstateManager.borough.model;

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
public class Borough {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private Long countryId;
    @ManyToOne
    @JoinColumn(name = "actualLeaderId")
    private Player actualLeader;
    private LocalDateTime leaderStartDate;
    private BigDecimal actualGold;
    @ManyToOne
    @JoinColumn(name = "goldAddedBy")
    private Player goldAddedBy;
    private LocalDateTime dateAdded;
    private Boolean emailSend;
}
