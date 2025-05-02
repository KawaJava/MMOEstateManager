package io.github.kawajava.MMOEstateManager.admin.common.exception;

import io.github.kawajava.MMOEstateManager.admin.common.exception.adminBorough.BoroughNameAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminBorough.BoroughSlugAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer.EmailAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer.NameAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer.SlugAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundExceptions(ResourceNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler({
            NameAlreadyExistsException.class,
            EmailAlreadyExistsException.class,
            SlugAlreadyExistsException.class,
            BoroughNameAlreadyExistsException.class,
            BoroughSlugAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleConflictExceptions(RuntimeException exception) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
    }

    private record ErrorResponse(int status, String message) {
    }
}

