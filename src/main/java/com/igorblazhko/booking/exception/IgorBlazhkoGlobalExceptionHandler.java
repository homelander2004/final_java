package com.igorblazhko.booking.exception;

import com.igorblazhko.booking.dto.error.IgorBlazhkoApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class IgorBlazhkoGlobalExceptionHandler {

    @ExceptionHandler(IgorBlazhkoResourceNotFoundException.class)
    public ResponseEntity<IgorBlazhkoApiErrorResponse> handleNotFound(IgorBlazhkoResourceNotFoundException exception,
                                                                      HttpServletRequest request) {
        log.warn("Resource not found: path={}, message={}", request.getRequestURI(), exception.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler({IgorBlazhkoBadRequestException.class, IllegalArgumentException.class})
    public ResponseEntity<IgorBlazhkoApiErrorResponse> handleBadRequest(RuntimeException exception,
                                                                        HttpServletRequest request) {
        log.warn("Bad request: path={}, message={}", request.getRequestURI(), exception.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request, null);
    }

    @ExceptionHandler(IgorBlazhkoConflictException.class)
    public ResponseEntity<IgorBlazhkoApiErrorResponse> handleConflict(IgorBlazhkoConflictException exception,
                                                                      HttpServletRequest request) {
        log.warn("Conflict detected: path={}, message={}", request.getRequestURI(), exception.getMessage());
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), request, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<IgorBlazhkoApiErrorResponse> handleValidation(MethodArgumentNotValidException exception,
                                                                        HttpServletRequest request) {
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() == null ? "Invalid value" : fieldError.getDefaultMessage(),
                        (left, right) -> right,
                        LinkedHashMap::new
                ));
                log.warn("Validation failed: path={}, errors={}", request.getRequestURI(), errors);
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<IgorBlazhkoApiErrorResponse> handleAccessDenied(AccessDeniedException exception,
                                                                          HttpServletRequest request) {
        log.warn("Access denied: path={}, message={}", request.getRequestURI(), exception.getMessage());
        return buildResponse(HttpStatus.FORBIDDEN, "Access denied", request, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<IgorBlazhkoApiErrorResponse> handleOther(Exception exception,
                                                                   HttpServletRequest request) {
        log.error("Unhandled server error", exception);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", request, null);
    }

    private ResponseEntity<IgorBlazhkoApiErrorResponse> buildResponse(HttpStatus status,
                                                                      String message,
                                                                      HttpServletRequest request,
                                                                      Map<String, String> validationErrors) {
        return ResponseEntity.status(status).body(new IgorBlazhkoApiErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                LocalDateTime.now(),
                validationErrors
        ));
    }
}