package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.dto.OpinionDto;
import io.github.kawajava.MMOEstateManager.common.AI.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionClassifier {

    private final AIService aiService;
    private final PromptProvider promptProvider;

    public Map<Long, AiOpinion> classifyOpinions(List<OpinionDto> opinions) {
        String prompt = promptProvider.getOpinionClassifierPrompt(opinions);
        String response = aiService.chat(prompt);
        return parseResponse(response);
    }

    private Map<Long, AiOpinion> parseResponse(String response) {
        var mapper = new ObjectMapper();
        try {
            List<Map<String, String>> parsed = mapper.readValue(response, new TypeReference<>() {});
            return parsed.stream().collect(Collectors.toMap(
                    m -> Long.valueOf(m.get("id")),
                    m -> AiOpinion.valueOf(m.get("opinion").toUpperCase())
            ));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing AI response: " + response, e);
        }
    }
}

