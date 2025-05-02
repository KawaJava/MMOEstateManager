package io.github.kawajava.MMOEstateManager.admin.player.service;

import io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer.EmailAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer.NameAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer.SlugAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.player.repository.AdminPlayerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminPlayerValidationServiceTest {

    private AdminPlayerValidationService validationService;

    @BeforeAll
    void setup() {
        AdminPlayerRepository mockRepo = mock(AdminPlayerRepository.class);

        List<AdminPlayer> existingPlayers = List.of(
                AdminPlayer.builder().id(1L).name("player1").email("player1@test.pl").slug("player1").build(),
                AdminPlayer.builder().id(2L).name("player2").email("player2@test.pl").slug("player2").build(),
                AdminPlayer.builder().id(3L).name("player3").email("player3@test.pl").slug("player3").build(),
                AdminPlayer.builder().id(4L).name("player4").email("player4@test.pl").slug("player4").build()
        );

        when(mockRepo.findAll()).thenReturn(existingPlayers);

        this.validationService = new AdminPlayerValidationService(mockRepo);

    }

    @Test
    void shouldPassValidationWhenPlayerIsUnique() {
        AdminPlayer newPlayer = AdminPlayer.builder()
                .id(null)
                .name("newPlayer")
                .email("new@domain.com")
                .slug("new-slug")
                .build();

        assertThatCode(() -> validationService.validateUniqueConstraints(newPlayer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowNameAlreadyExistsExceptionWhenNameExists() {
        AdminPlayer newPlayer = AdminPlayer.builder()
                .id(null)
                .name("player1")
                .email("other@domain.com")
                .slug("new-slug")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newPlayer))
                .isInstanceOf(NameAlreadyExistsException.class)
                .hasMessageContaining("player1");
    }

    @Test
    void shouldThrowEmailAlreadyExistsExceptionWhenEmailExists() {
        AdminPlayer newPlayer = AdminPlayer.builder()
                .id(null)
                .name("new")
                .email("player2@test.pl")
                .slug("new-slug")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newPlayer))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("player2@test.pl");
    }

    @Test
    void shouldThrowSlugAlreadyExistsExceptionWhenSlugExists() {
        AdminPlayer newPlayer = AdminPlayer.builder()
                .id(null)
                .name("new")
                .email("new@domain.com")
                .slug("player3")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newPlayer))
                .isInstanceOf(SlugAlreadyExistsException.class)
                .hasMessageContaining("player3");
    }

    @Test
    void shouldThrowFirstValidationExceptionWhenMultipleFieldsConflict() {
        AdminPlayer newPlayer = AdminPlayer.builder()
                .id(null)
                .name("player2")
                .email("player3@test.pl")
                .slug("player4")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newPlayer))
                .isInstanceOf(NameAlreadyExistsException.class);
    }
    @Test
    void shouldPassValidationWhenUpdatingSamePlayer() {
        AdminPlayer existingPlayer = AdminPlayer.builder()
                .id(1L)
                .name("player1")
                .email("player1@test.pl")
                .slug("player1")
                .build();

        assertThatCode(() -> validationService.validateUniqueConstraints(existingPlayer))
                .doesNotThrowAnyException();
    }

}