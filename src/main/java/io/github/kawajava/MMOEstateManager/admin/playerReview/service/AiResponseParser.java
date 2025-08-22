package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import io.github.kawajava.MMOEstateManager.admin.playerReview.service.dto.OpinionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AiResponseParser {

    private final ObjectMapper mapper;

    public Map<Long, AiOpinion> parse(String response) {
        try {
            List<OpinionResult> results = mapper.readValue(response, new TypeReference<>() {});
            return results.stream()
                    .collect(Collectors.toMap(OpinionResult::id, OpinionResult::opinion));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing AI response: " + response, e);
        }
    }

}

