package io.github.kawajava.MMOEstateManager.admin.country.model;

import io.github.kawajava.MMOEstateManager.admin.player.model.Clan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "country")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private Long actualSheriffId;
    private BigDecimal goldLimit;
    private LocalDateTime sheriffStartDate;

}
