package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiOpinionScheduler {

    private final AiOpinionService aiOpinionService;

    @Scheduled(cron = "0 * * * * *")
    public void scheduleAiClassification() {
        aiOpinionService.processOpinions();
    }
}

