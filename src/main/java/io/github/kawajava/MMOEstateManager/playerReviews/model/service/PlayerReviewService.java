package io.github.kawajava.MMOEstateManager.playerReviews.model.service;

import io.github.kawajava.MMOEstateManager.playerReviews.model.PlayerReview;
import io.github.kawajava.MMOEstateManager.playerReviews.model.controller.dto.PlayerReviewDTO;
import io.github.kawajava.MMOEstateManager.playerReviews.model.repository.PlayerReviewRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlayerReviewService {

    private final PlayerReviewRepository playerReviewRepository;

    public PlayerReview addReview(PlayerReviewDTO review) {
        return playerReviewRepository.save(buildPlayerReview(review));
    }

    private PlayerReview buildPlayerReview(PlayerReviewDTO review) {
        return PlayerReview.builder()
                .playerId(review.playerId())
                .authorName(cleanText(review.authorName()))
                .content(cleanText(review.content()))
                .note(review.note())
                .createdAt(LocalDateTime.now())
                .accepted(false)
                .build();
    }

    private String cleanText(String text) {
        return Jsoup.clean(text, Safelist.none());
    }

}
