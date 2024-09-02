package io.github.kawajava.MMOEstateManager.admin.player.model;

import io.github.kawajava.MMOEstateManager.common.model.Clan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "player")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Clan clan;
    private boolean isActive;
    private LocalDate created;
    private String slug;
}
