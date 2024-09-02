package io.github.kawajava.MMOEstateManager.player.model;

import io.github.kawajava.MMOEstateManager.admin.player.model.Clan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {
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
