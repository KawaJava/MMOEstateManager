package io.github.kawajava.MMOEstateManager.playerReviews.model.repository;

import io.github.kawajava.MMOEstateManager.playerReviews.model.PlayerReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerReviewRepository extends JpaRepository<PlayerReview, Long> {
    Page<PlayerReview> findByPlayerIdAndAcceptedTrueOrderByCreatedAtDesc(Long playerId, Pageable pageable);
}
