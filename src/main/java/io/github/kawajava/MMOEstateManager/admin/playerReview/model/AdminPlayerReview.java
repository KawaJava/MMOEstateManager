package io.github.kawajava.MMOEstateManager.admin.playerReview.model;

import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "player_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminPlayerReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "playerId")
    private AdminPlayer player;
    private String authorName;
    private Byte note;
    private String content;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private AiOpinion aiOpinion;
    private Boolean accepted;
}
