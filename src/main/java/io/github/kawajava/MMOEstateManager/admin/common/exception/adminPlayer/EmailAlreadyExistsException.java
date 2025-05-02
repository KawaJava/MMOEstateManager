package io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Gracz z adresem email '" + email + "' już istnieje.");
    }
}
