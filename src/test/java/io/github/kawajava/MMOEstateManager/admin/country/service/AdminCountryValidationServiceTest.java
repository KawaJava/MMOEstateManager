package io.github.kawajava.MMOEstateManager.admin.country.service;

import io.github.kawajava.MMOEstateManager.admin.common.exception.adminCountry.CountryNameAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminCountry.CountrySlugAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.country.model.AdminCountry;
import io.github.kawajava.MMOEstateManager.admin.country.repository.AdminCountryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminCountryValidationServiceTest {

    private AdminCountryValidationService validationService;

    @BeforeAll
    void setup() {
        AdminCountryRepository mockRepo = mock(AdminCountryRepository.class);

        List<AdminCountry> existingCountries = List.of(
                AdminCountry.builder().id(1L).name("country1").slug("country1").build(),
                AdminCountry.builder().id(2L).name("country2").slug("country2").build(),
                AdminCountry.builder().id(3L).name("country3").slug("country3").build(),
                AdminCountry.builder().id(4L).name("country4").slug("country4").build()
        );

        when(mockRepo.findAll()).thenReturn(existingCountries);

        this.validationService = new AdminCountryValidationService(mockRepo);
    }

    @Test
    void shouldPassValidationWhenCountryIsUnique() {
        AdminCountry newCountry = AdminCountry.builder()
                .id(null)
                .name("newCountry")
                .slug("new-slug")
                .build();

        assertThatCode(() -> validationService.validateUniqueConstraints(newCountry))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowNameAlreadyExistsExceptionWhenNameExists() {
        AdminCountry newCountry = AdminCountry.builder()
                .id(null)
                .name("country1")
                .slug("unique-slug")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newCountry))
                .isInstanceOf(CountryNameAlreadyExistsException.class)
                .hasMessageContaining("country1");
    }

    @Test
    void shouldThrowSlugAlreadyExistsExceptionWhenSlugExists() {
        AdminCountry newCountry = AdminCountry.builder()
                .id(null)
                .name("uniqueName")
                .slug("country2")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newCountry))
                .isInstanceOf(CountrySlugAlreadyExistsException.class)
                .hasMessageContaining("country2");
    }

    @Test
    void shouldThrowFirstValidationExceptionWhenMultipleFieldsConflict() {
        AdminCountry newCountry = AdminCountry.builder()
                .id(null)
                .name("country3")
                .slug("country4")
                .build();

        assertThatThrownBy(() -> validationService.validateUniqueConstraints(newCountry))
                .isInstanceOf(CountryNameAlreadyExistsException.class);
    }

    @Test
    void shouldPassValidationWhenUpdatingSameCountry() {
        AdminCountry existingCountry = AdminCountry.builder()
                .id(1L)
                .name("country1")
                .slug("country1")
                .build();

        assertThatCode(() -> validationService.validateUniqueConstraints(existingCountry))
                .doesNotThrowAnyException();
    }
}
