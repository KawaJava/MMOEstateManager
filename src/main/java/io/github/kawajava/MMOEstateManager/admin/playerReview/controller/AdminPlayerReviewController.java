package io.github.kawajava.MMOEstateManager.admin.playerReview.controller;


import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AdminPlayerReview;
import io.github.kawajava.MMOEstateManager.admin.playerReview.service.AdminPlayerReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/player-reviews")
public class AdminPlayerReviewController {

    private final AdminPlayerReviewService adminPlayerService;

    @GetMapping
    public Page<AdminPlayerReview> getReviews(Pageable page){
        return adminPlayerService.getReviews(page);
    }

    @GetMapping("/to-accept")
    public List<AdminPlayerReview> getReviewsToAccept(){
        return adminPlayerService.getReviewsToAccept();
    }

    @PatchMapping("/{id}/accept")
    public void acceptPlayerReview(@PathVariable Long id){
        adminPlayerService.acceptPlayerReview(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        adminPlayerService.delete(id);
    }

}
