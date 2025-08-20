package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.OpinionDto;
import io.github.kawajava.MMOEstateManager.common.AI.service.AIService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AiClient {

    private final AIService aiService;
    private final PromptProvider promptProvider;

    public AiClient(AIService aiService, PromptProvider promptProvider) {
        this.aiService = aiService;
        this.promptProvider = promptProvider;
    }

    public Map<Long, AiOpinion> classifyBatch(List<OpinionDto> batch) {
        String filledPrompt = promptProvider.getOpinionClassifierPrompt(batch);

        String response = aiService.chat(filledPrompt);

        return parseResponse(response);
    }

    private Map<Long, AiOpinion> parseResponse(String response) {
        try {
            var mapper = new ObjectMapper();
            List<OpinionResult> results = mapper.readValue(response,
                    new TypeReference<>() {});
            return results.stream()
                    .collect(Collectors.toMap(
                            OpinionResult::id,
                            OpinionResult::opinion
                    ));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing AI response: " + response, e);
        }
    }

    private record OpinionResult(Long id, AiOpinion opinion) {}
}

