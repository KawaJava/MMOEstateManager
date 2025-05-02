package io.github.kawajava.MMOEstateManager.admin.common.exception.adminBorough;

public class BoroughNameAlreadyExistsException extends RuntimeException {
    public BoroughNameAlreadyExistsException(String name) {
        super("Gmina o nazwie '" + name + "' już istnieje.");
    }
}

