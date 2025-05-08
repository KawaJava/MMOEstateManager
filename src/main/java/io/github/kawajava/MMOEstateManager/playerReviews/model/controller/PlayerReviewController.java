package io.github.kawajava.MMOEstateManager.playerReviews.model.controller;


import io.github.kawajava.MMOEstateManager.playerReviews.model.PlayerReview;
import io.github.kawajava.MMOEstateManager.playerReviews.model.controller.dto.PlayerReviewDTO;
import io.github.kawajava.MMOEstateManager.playerReviews.model.service.PlayerReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlayerReviewController {

    private final PlayerReviewService reviewService;

    @PostMapping("players/{id}/add-review")
    public PlayerReview addReview(@RequestBody @Valid PlayerReviewDTO reviewDto) {
        return reviewService.addReview(reviewDto);
    }
}
