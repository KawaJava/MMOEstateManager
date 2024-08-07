package io.github.kawajava.MMOEstateManager.admin.common.exception;

public class NotExistingPlayerException extends RuntimeException {
    public NotExistingPlayerException(Long id) {
        super("Player with the given ID: " + id + " does not exist!");
    }
}
