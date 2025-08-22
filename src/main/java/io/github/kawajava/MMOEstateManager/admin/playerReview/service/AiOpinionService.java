package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AdminPlayerReview;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.dto.OpinionDto;
import io.github.kawajava.MMOEstateManager.admin.playerReview.repository.AdminPlayerReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class AiOpinionService {

    private final AdminPlayerReviewRepository opinionRepository;
    private final AiClient aiClient;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    private static final int BATCH_SIZE = 50;

    @Transactional
    public void processOpinions() {
        List<AdminPlayerReview> records = opinionRepository.findTop1000ByAiOpinionIsNullAndAcceptedFalse();

        List<List<AdminPlayerReview>> partitions = partition(records, BATCH_SIZE);

        List<CompletableFuture<Void>> futures = partitions.stream()
                .map(batch -> CompletableFuture.runAsync(() -> processBatch(batch), executor))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private void processBatch(List<AdminPlayerReview> batch) {
        List<OpinionDto> dtos = batch.stream()
                .map(op -> new OpinionDto(op.getId(), op.getContent(), null))
                .toList();

        Map<Long, AiOpinion> results = aiClient.classifyBatch(dtos);

        batch.forEach(op -> op.setAiOpinion(results.getOrDefault(op.getId(), AiOpinion.NEEDS_REVIEW)));

        opinionRepository.saveAll(batch);
    }

    private <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }
}

