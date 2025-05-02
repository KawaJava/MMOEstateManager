package io.github.kawajava.MMOEstateManager.admin.borough.service;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.borough.repository.AdminBoroughRepository;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminBorough.BoroughNameAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminBorough.BoroughSlugAlreadyExistsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminBoroughValidationServiceTest {

    private AdminBoroughValidationService validationService;

    @BeforeAll
    void setup() {
        AdminBoroughRepository mockRepo = mock(AdminBoroughRepository.class);

        List<AdminBorough> existingBoroughs = List.of(
                AdminBorough.builder().id(1L).name("borough1").slug("borough1").build(),
                AdminBorough.builder().id(2L).name("borough2").slug("borough2").build(),
                AdminBorough.builder().id(3L).name("borough3").slug("borough3").build(),
                AdminBorough.builder().id(4L).name("borough4").slug("borough4").build()
        );

        when(mockRepo.findAll()).thenReturn(existingBoroughs);

        this.validationService = new AdminBoroughValidationService(mockRepo);
    }

    @Test
    void shouldPassValidationWhenBoroughIsUnique() {
        AdminBorough newBorough = AdminBorough.builder()
                .id(null)
                .name("newBorough")
                .slug("new-slug")
                .build();

        assertThatCode(() -> validationService.validateUniqueConstraints(newBorough))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowNameAlreadyExistsExceptionWhenNameExists() {
        AdminBorough newBorough = AdminBorough.builder()
                .id(null)
                .name("borough1")
                .slug("unique-slug")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newBorough))
                .isInstanceOf(BoroughNameAlreadyExistsException.class)
                .hasMessageContaining("borough1");
    }

    @Test
    void shouldThrowSlugAlreadyExistsExceptionWhenSlugExists() {
        AdminBorough newBorough = AdminBorough.builder()
                .id(null)
                .name("uniqueName")
                .slug("borough2")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newBorough))
                .isInstanceOf(BoroughSlugAlreadyExistsException.class)
                .hasMessageContaining("borough2");
    }

    @Test
    void shouldThrowFirstValidationExceptionWhenMultipleFieldsConflict() {
        AdminBorough newBorough = AdminBorough.builder()
                .id(null)
                .name("borough3")
                .slug("borough4")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newBorough))
                .isInstanceOf(BoroughNameAlreadyExistsException.class);
    }

    @Test
    void shouldPassValidationWhenUpdatingSameBorough() {
        AdminBorough existingBorough = AdminBorough.builder()
                .id(1L)
                .name("borough1")
                .slug("borough1")
                .build();

        assertThatCode(() -> validationService.validateUniqueConstraints(existingBorough))
                .doesNotThrowAnyException();
    }
}
