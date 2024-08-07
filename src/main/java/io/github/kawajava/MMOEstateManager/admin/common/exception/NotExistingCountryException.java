package io.github.kawajava.MMOEstateManager.admin.common.exception;

public class NotExistingCountryException extends RuntimeException {
    public NotExistingCountryException(Long id) {
        super("Country with the given ID: " + id + " does not exist!");
    }
}
