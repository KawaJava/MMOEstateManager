package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AdminPlayerReview;
import io.github.kawajava.MMOEstateManager.admin.playerReview.repository.AdminPlayerReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPlayerReviewService {

    private final AdminPlayerReviewRepository playerReviewRepository;

    public Page<AdminPlayerReview> getReviews(Pageable pageable) {
        return playerReviewRepository.findAll(pageable);
    }

    public List<AdminPlayerReview> getReviewsToAccept() {
        return playerReviewRepository.findByAcceptedFalse();
    }

    @Transactional
    public void acceptPlayerReview(Long id) {
        playerReviewRepository.acceptPlayerReview(id);
    }

    public void delete(Long id) {
        playerReviewRepository.deleteById(id);
    }
}
