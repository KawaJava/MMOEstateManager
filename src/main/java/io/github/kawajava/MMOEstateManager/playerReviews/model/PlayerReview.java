package io.github.kawajava.MMOEstateManager.playerReviews.model;

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
public class PlayerReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long playerId;
    private String authorName;
    private Byte note;
    private String content;
    private LocalDateTime createdAt;
    private Boolean accepted;
}

