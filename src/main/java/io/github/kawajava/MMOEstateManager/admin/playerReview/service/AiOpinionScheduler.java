package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiOpinionScheduler {

    private final AiOpinionService aiOpinionService;

    @Scheduled(cron = "${cron.player.review.ai.opinion.expression}")
    public void scheduleAiClassification() {
        aiOpinionService.processOpinions();
    }
}

