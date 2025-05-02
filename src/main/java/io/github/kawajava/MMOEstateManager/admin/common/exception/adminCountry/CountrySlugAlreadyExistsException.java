package io.github.kawajava.MMOEstateManager.admin.common.exception.adminCountry;

public class CountrySlugAlreadyExistsException extends RuntimeException {
    public CountrySlugAlreadyExistsException(String slug) {
        super("Hrabstwo ze slugiem '" + slug + "' już istnieje.");
    }
}

