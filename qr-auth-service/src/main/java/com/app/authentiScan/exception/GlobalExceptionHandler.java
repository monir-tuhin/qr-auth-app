package com.app.authentiScan.exception;

import com.app.authentiScan.utils.ApiResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApiResponseFactory apiResponseFactory;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("From ResourceNotFoundException: {}", ex.getMessage(), ex);
        return apiResponseFactory.errorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(UserInformException.class)
    public ResponseEntity<?> handleUserInformException(UserInformException ex) {
        log.error("From UserInformException: {}", ex.getMessage(), ex);
        return apiResponseFactory.errorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(UniqueConstraintViolationException.class)
    public ResponseEntity<?> handleUniqueConstraintViolationException(UniqueConstraintViolationException ex) {
        log.error("From UniqueConstraintViolationException: {}", ex.getMessage(), ex);
        return apiResponseFactory.errorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        log.error("From RuntimeException: {}", ex.getMessage(), ex);
        return apiResponseFactory.errorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        log.error("From GlobalException: {}", ex.getMessage(), ex);
        return apiResponseFactory.errorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
