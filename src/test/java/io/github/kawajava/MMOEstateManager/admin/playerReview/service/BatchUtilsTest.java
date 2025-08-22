package io.github.kawajava.MMOEstateManager.admin.playerReview.service;

import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AdminPlayerReview;
import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;
import io.github.kawajava.MMOEstateManager.admin.playerReview.repository.AdminPlayerReviewRepository;
import io.github.kawajava.MMOEstateManager.common.model.Clan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchUtilsTest {

    @Mock
    private AdminPlayerReviewRepository repo;

    @Mock
    private AiClient aiClient;

    private BatchUtils batchUtils;

    @BeforeEach
    void setup() {
        batchUtils = new BatchUtils(repo, aiClient);
    }

    @Test
    void shouldPartitionListCorrectly() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().toList();

        List<List<Integer>> result = batchUtils.partition(list, 3);

        assertThat(result).hasSize(4);
        assertThat(result.get(0)).containsExactly(1, 2, 3);
        assertThat(result.get(3)).containsExactly(10);
    }

    @Test
    void shouldReturnEmptyOnNullOrEmptyList() {
        assertThat(batchUtils.partition(null, 5)).isEmpty();
        assertThat(batchUtils.partition(List.of(), 5)).isEmpty();
    }

    @Test
    void shouldReturnSinglePartitionIfSizeGreaterThanList() {
        List<Integer> list = List.of(1, 2, 3);
        List<List<Integer>> result = batchUtils.partition(list, 10);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).containsExactly(1, 2, 3);
    }


    @Test
    void shouldPutEachElementInSeparatePartitionIfSizeOne() {
        List<Integer> list = List.of(1, 2, 3);
        List<List<Integer>> result = batchUtils.partition(list, 1);

        assertThat(result).hasSize(3);
        assertThat(result).allSatisfy(part -> assertThat(part).hasSize(1));
    }

    @Test
    void shouldProcessBatchAndSaveOpinions() {

        when(aiClient.classifyBatch(anyList()))
                .thenReturn(Map.of(1L, AiOpinion.SPAM, 2L, AiOpinion.OFFENSIVE));

        batchUtils.processBatch(List.of(r1, r2));

        assertThat(r1.getAiOpinion()).isEqualTo(AiOpinion.SPAM);
        assertThat(r2.getAiOpinion()).isEqualTo(AiOpinion.OFFENSIVE);
        verify(repo).saveAll(List.of(r1, r2));
    }

    @Test
    void shouldSetNeedsReviewIfAiDoesNotReturnOpinion() {

        when(aiClient.classifyBatch(anyList())).thenReturn(Map.of());

        batchUtils.processBatch(List.of(r1));

        assertThat(r1.getAiOpinion()).isEqualTo(AiOpinion.NEEDS_REVIEW);
        verify(repo).saveAll(List.of(r1));
    }

    @Test
    void shouldPropagateExceptionFromAiClient() {
        when(aiClient.classifyBatch(anyList())).thenThrow(new RuntimeException("AI error"));

        assertThatThrownBy(() -> batchUtils.processBatch(List.of(r1)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("AI error");
    }
    AdminPlayer player1 = AdminPlayer.builder()
            .id(1L)
            .name("Player One")
            .email("player1@example.com")
            .clan(Clan.Clan1)
            .isActive(true)
            .created(LocalDate.of(2020, 1, 15))
            .slug("player-one")
            .build();
    AdminPlayer player2 = AdminPlayer.builder()
            .id(2L)
            .name("Player Two")
            .email("player2@example.com")
            .clan(Clan.Clan3)
            .isActive(true)
            .created(LocalDate.of(2021, 6, 30))
            .slug("player-two")
            .build();
    AdminPlayerReview r1 = AdminPlayerReview.builder()
            .id(1L)
            .player(player1)
            .authorName("Author1")
            .note((byte) 5)
            .content("Review content 1")
            .createdAt(LocalDateTime.now())
            .aiOpinion(null)
            .accepted(false)
            .build();

    AdminPlayerReview r2 = AdminPlayerReview.builder()
            .id(2L)
            .player(player2)
            .authorName("Author2")
            .note((byte) 3)
            .content("Review content 2")
            .createdAt(LocalDateTime.now())
            .aiOpinion(null)
            .accepted(false)
            .build();
}
