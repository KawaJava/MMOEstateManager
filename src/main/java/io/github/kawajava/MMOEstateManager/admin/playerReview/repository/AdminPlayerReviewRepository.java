package io.github.kawajava.MMOEstateManager.admin.playerReview.repository;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AdminPlayerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminPlayerReviewRepository extends JpaRepository<AdminPlayerReview, Long> {
    @Query("update AdminPlayerReview r set r.accepted=true where r.id=:id")
    @Modifying
    void acceptPlayerReview(Long id);
    List<AdminPlayerReview> findByAcceptedFalse();
}
