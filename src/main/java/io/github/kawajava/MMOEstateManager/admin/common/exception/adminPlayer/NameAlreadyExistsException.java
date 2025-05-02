package io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer;

public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException(String name) {
        super("Gracz o nazwie '" + name + "' już istnieje.");
    }
}
