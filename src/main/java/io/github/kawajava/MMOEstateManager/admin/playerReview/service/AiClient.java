package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.OpinionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AiClient {

    public Map<Long, AiOpinion> classifyBatch(List<OpinionDto> batch) {
        return batch.stream()
                .collect(Collectors.toMap(
                        OpinionDto::id,
                        dto -> AiOpinion.APPROPRIATE
                ));
    }
}

