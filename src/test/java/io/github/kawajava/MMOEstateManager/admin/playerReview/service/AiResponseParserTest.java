package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class AiResponseParserTest {

    private AiResponseParser parser;

    @BeforeEach
    void setUp() {
        parser = new AiResponseParser(new ObjectMapper());
    }

    @Test
    void shouldParseAllEnumValues() {

        Map<Long, AiOpinion> result = parser.parse(json);

        assertThat(result).hasSize(8);
        assertThat(result.get(1L)).isEqualTo(AiOpinion.SPAM);
        assertThat(result.get(2L)).isEqualTo(AiOpinion.OFFENSIVE);
        assertThat(result.get(3L)).isEqualTo(AiOpinion.APPROPRIATE);
        assertThat(result.get(4L)).isEqualTo(AiOpinion.IRRELEVANT);
        assertThat(result.get(5L)).isEqualTo(AiOpinion.HARASSMENT);
        assertThat(result.get(6L)).isEqualTo(AiOpinion.POSITIVE);
        assertThat(result.get(7L)).isEqualTo(AiOpinion.NEEDS_REVIEW);
        assertThat(result.get(8L)).isEqualTo(AiOpinion.OTHER);
    }

    @Test
    void shouldThrowExceptionOnUnknownEnumValue() {
        String json = """
                [
                  {"id": 1, "opinion": "NOT_EXISTING"}
                ]
                """;

        assertThatThrownBy(() -> parser.parse(json))
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(com.fasterxml.jackson.databind.exc.InvalidFormatException.class);

    }

    @Test
    void shouldThrowExceptionOnNullOpinion() {
        String json = """
                [
                  {"id": 1, "opinion": null}
                ]
                """;

        assertThatThrownBy(() -> parser.parse(json))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error parsing AI response");
    }

    @Test
    void shouldThrowExceptionOnMissingOpinionField() {
        String json = """
                [
                  {"id": 1}
                ]
                """;
        assertThatThrownBy(() -> parser.parse(json)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldReturnEmptyMapOnEmptyArray() {
        String json = "[]";

        Map<Long, AiOpinion> result = parser.parse(json);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowExceptionOnEmptyString() {
        String json = "";

        assertThatThrownBy(() -> parser.parse(json)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldThrowExceptionOnNullInput() {

        assertThatThrownBy(() -> parser.parse(null))
                .isInstanceOf(RuntimeException.class);
    }
    String json = """
                [
                  {"id": 1, "opinion": "SPAM"},
                  {"id": 2, "opinion": "OFFENSIVE"},
                  {"id": 3, "opinion": "APPROPRIATE"},
                  {"id": 4, "opinion": "IRRELEVANT"},
                  {"id": 5, "opinion": "HARASSMENT"},
                  {"id": 6, "opinion": "POSITIVE"},
                  {"id": 7, "opinion": "NEEDS_REVIEW"},
                  {"id": 8, "opinion": "OTHER"}
                ]
                """;
}
