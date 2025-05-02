package io.github.kawajava.MMOEstateManager.admin.common.exception.adminBorough;

public class BoroughSlugAlreadyExistsException extends RuntimeException {
    public BoroughSlugAlreadyExistsException(String slug) {
        super("Gmina ze slugiem '" + slug + "' już istnieje.");
    }
}

