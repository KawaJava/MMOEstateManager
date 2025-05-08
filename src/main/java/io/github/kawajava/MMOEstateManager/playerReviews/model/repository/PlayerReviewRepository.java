package io.github.kawajava.MMOEstateManager.playerReviews.model.repository;

import io.github.kawajava.MMOEstateManager.playerReviews.model.PlayerReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerReviewRepository extends JpaRepository<PlayerReview, Long> {
}
