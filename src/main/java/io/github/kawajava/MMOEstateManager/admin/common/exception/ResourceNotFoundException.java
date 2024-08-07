package io.github.kawajava.MMOEstateManager.admin.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " with the given ID: " + id + " does not exist!");
    }
}
