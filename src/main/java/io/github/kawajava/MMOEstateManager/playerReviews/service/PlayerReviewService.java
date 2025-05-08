package io.github.kawajava.MMOEstateManager.playerReviews.service;

import io.github.kawajava.MMOEstateManager.playerReviews.model.PlayerReview;
import io.github.kawajava.MMOEstateManager.playerReviews.controller.dto.PlayerReviewDTO;
import io.github.kawajava.MMOEstateManager.playerReviews.repository.PlayerReviewRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerReviewService {

    private final PlayerReviewRepository playerReviewRepository;

    public PlayerReview addReview(PlayerReviewDTO review) {
        return playerReviewRepository.save(buildPlayerReview(review));
    }

    public List<PlayerReview> getAcceptedReviews(Long playerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return playerReviewRepository
                .findByPlayerIdAndAcceptedTrueOrderByCreatedAtDesc(playerId, pageable)
                .getContent();
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
