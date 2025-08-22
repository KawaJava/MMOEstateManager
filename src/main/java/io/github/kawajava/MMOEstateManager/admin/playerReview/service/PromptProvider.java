package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.dto.OpinionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class PromptProvider {

    private final String opinionClassifierPrompt;

    public PromptProvider(@Value("classpath:prompts/opinion-classifier.prompt") Resource resource) throws IOException {
        this.opinionClassifierPrompt = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public String getOpinionClassifierPrompt(List<OpinionDto> opinions) {
        var sb = new StringBuilder();
        for (OpinionDto dto : opinions) {
            sb.append("{id: ").append(dto.id())
                    .append(", text: \"").append(dto.content().replace("\"", "'")).append("\"}\n");
        }
        return opinionClassifierPrompt.replace("{{opinions}}", sb.toString());
    }
}


