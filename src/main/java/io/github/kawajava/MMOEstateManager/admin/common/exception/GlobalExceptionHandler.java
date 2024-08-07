package io.github.kawajava.MMOEstateManager.admin.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotExistingPlayerException.class, NotExistingCountryException.class, ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundExceptions(RuntimeException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), message);
    }
    private record ErrorResponse(int status, String message) {
    }
}
