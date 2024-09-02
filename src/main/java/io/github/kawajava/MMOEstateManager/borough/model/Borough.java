package io.github.kawajava.MMOEstateManager.borough.model;

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
    private Long actualLeaderId;
    private LocalDateTime leaderStartDate;
    private BigDecimal actualGold;
    private Long goldAddedBy;
    private LocalDateTime dateAdded;
    private Boolean emailSend;
}
