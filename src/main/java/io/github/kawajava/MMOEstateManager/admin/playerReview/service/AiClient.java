package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.dto.OpinionDto;
import io.github.kawajava.MMOEstateManager.common.AI.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiClient {

    private final AIService aiService;
    private final PromptProvider promptProvider;
    private final AiResponseParser parser;

    public Map<Long, AiOpinion> classifyBatch(List<OpinionDto> batch) {
        String filledPrompt = promptProvider.getOpinionClassifierPrompt(batch);
        String response = aiService.chat(filledPrompt);
        return parser.parse(response);
    }
}

