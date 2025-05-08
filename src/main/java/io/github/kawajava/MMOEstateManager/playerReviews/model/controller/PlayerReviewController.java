package io.github.kawajava.MMOEstateManager.playerReviews.model.controller;


import io.github.kawajava.MMOEstateManager.playerReviews.model.PlayerReview;
import io.github.kawajava.MMOEstateManager.playerReviews.model.controller.dto.PlayerReviewDTO;
import io.github.kawajava.MMOEstateManager.playerReviews.model.service.PlayerReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerReviewController {

    private final PlayerReviewService reviewService;

    @PostMapping("players/{id}/add-review")
    public PlayerReview addReview(@RequestBody @Valid PlayerReviewDTO reviewDto) {
        return reviewService.addReview(reviewDto);
    }

    @GetMapping("players/{id}/reviews")
    public List<PlayerReview> getAcceptedReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return reviewService.getAcceptedReviews(id, page, size);
    }
}
