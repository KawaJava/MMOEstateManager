package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AdminPlayerReview;
import io.github.kawajava.MMOEstateManager.admin.playerReview.repository.AdminPlayerReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class AiOpinionService {

    private final AdminPlayerReviewRepository opinionRepository;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final BatchUtils batchUtils;

    private static final int BATCH_SIZE = 50;

    @Transactional
    public void processOpinions() {
        List<AdminPlayerReview> records = opinionRepository.findTop1000ByAiOpinionIsNullAndAcceptedFalse();

        List<List<AdminPlayerReview>> partitions = batchUtils.partition(records, BATCH_SIZE);

        List<CompletableFuture<Void>> futures = partitions.stream()
                .map(batch -> CompletableFuture.runAsync(() -> batchUtils.processBatch(batch), executor))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

}

