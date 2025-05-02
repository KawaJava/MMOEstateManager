package io.github.kawajava.MMOEstateManager.admin.common.exception.adminCountry;

public class CountryNameAlreadyExistsException extends RuntimeException {
    public CountryNameAlreadyExistsException(String name) {
        super("Hrabstwo o nazwie '" + name + "' już istnieje.");
    }
}

