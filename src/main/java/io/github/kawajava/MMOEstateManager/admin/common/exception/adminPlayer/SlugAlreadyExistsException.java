package io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer;


public class SlugAlreadyExistsException extends RuntimeException {
    public SlugAlreadyExistsException(String slug) {
        super("Gracz z slugiem '" + slug + "' już istnieje.");
    }
}
