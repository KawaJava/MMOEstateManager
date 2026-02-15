package io.github.kawajava.MMOEstateManager.playerReview.controller.dto;

import jakarta.validation.constraints.*;

public record PlayerReviewDTO(
        @NotNull Long playerId,
        @NotBlank @Size(max = 60) String authorName,
        @NotNull @Min(1) @Max(5) Byte note,
        @NotBlank @Size(max = 600) String content
) {
}
