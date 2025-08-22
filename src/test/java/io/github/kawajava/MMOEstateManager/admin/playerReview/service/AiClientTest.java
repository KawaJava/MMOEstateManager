package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.dto.OpinionDto;
import io.github.kawajava.MMOEstateManager.common.AI.service.AIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AiClientTest {

    private AIService aiService;
    private PromptProvider promptProvider;
    private AiResponseParser parser;
    private AiClient aiClient;

    @BeforeEach
    void setUp() {
        aiService = mock(AIService.class);
        promptProvider = mock(PromptProvider.class);
        parser = new AiResponseParser(new com.fasterxml.jackson.databind.ObjectMapper());
        aiClient = new AiClient(aiService, promptProvider, parser);
    }

    @Test
    void shouldClassifyBatchCorrectly() {
        when(promptProvider.getOpinionClassifierPrompt(batch)).thenReturn("PROMPT");
        when(aiService.chat("PROMPT")).thenReturn("""
                [{"id": 1, "opinion": "SPAM"}]
                """);

        Map<Long, AiOpinion> result = aiClient.classifyBatch(batch);

        assertThat(result).containsEntry(1L, AiOpinion.SPAM);
    }

    @Test
    void shouldReturnEmptyMapIfAiReturnsEmptyArray() {

        when(promptProvider.getOpinionClassifierPrompt(batch)).thenReturn("PROMPT");
        when(aiService.chat("PROMPT")).thenReturn("[]");

        Map<Long, AiOpinion> result = aiClient.classifyBatch(batch);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowExceptionIfAiReturnsInvalidJson() {

        when(promptProvider.getOpinionClassifierPrompt(batch)).thenReturn("PROMPT");
        when(aiService.chat("PROMPT")).thenReturn("NOT_JSON");

        assertThatThrownBy(() -> aiClient.classifyBatch(batch))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error parsing AI response");
    }

    List<OpinionDto> batch= List.of(new OpinionDto(1L, "text1", null));

}
