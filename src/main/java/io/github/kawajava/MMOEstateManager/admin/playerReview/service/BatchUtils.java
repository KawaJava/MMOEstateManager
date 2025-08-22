package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AdminPlayerReview;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.dto.OpinionDto;
import io.github.kawajava.MMOEstateManager.admin.playerReview.repository.AdminPlayerReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
final class BatchUtils {

    private final AdminPlayerReviewRepository opinionRepository;
    private final AiClient aiClient;

    <T> List<List<T>> partition(List<T> list, int size) {
        if (list == null || list.isEmpty()) return List.of();
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }
    void processBatch(List<AdminPlayerReview> batch) {
        List<OpinionDto> dtos = batch.stream()
                .map(op -> new OpinionDto(op.getId(), op.getContent(), null))
                .toList();

        Map<Long, AiOpinion> results = aiClient.classifyBatch(dtos);

        batch.forEach(op -> op.setAiOpinion(results.getOrDefault(op.getId(), AiOpinion.NEEDS_REVIEW)));

        opinionRepository.saveAll(batch);
    }
}

