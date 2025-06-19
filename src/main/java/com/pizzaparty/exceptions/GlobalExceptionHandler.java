package com.pizzaparty.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * Global exception handling class.
 * This class handles exceptions globally for the application, returning custom HTTP responses
 * based on the type of exception that occurs. It uses the {@link RestControllerAdvice} annotation to apply
 * to all exceptions not explicitly handled in the controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles all RuntimeException type exceptions.
     * @param ex The RuntimeException.
     * @return An HTTP response with the exception message and status 500 (INTERNAL_SERVER_ERROR).
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        logger.error("Internal error: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles IllegalArgumentException.
     * @param ex The IllegalArgumentException.
     * @return An HTTP response with a custom error message and status 400 (BAD_REQUEST).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("Invalid argument: ", ex);
        return new ResponseEntity<>("Invalid argument: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles NullPointerException.
     * @param ex The NullPointerException.
     * @return An HTTP response with a custom error message and status 500 (INTERNAL_SERVER_ERROR).
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        logger.error("Internal error (NullPointer): ", ex);
        return new ResponseEntity<>("An internal error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles AccessDeniedException.
     * @param ex The AccessDeniedException.
     * @return An HTTP response with a custom error message and status 403 (FORBIDDEN).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        logger.warn("Access denied: ", ex);
        return new ResponseEntity<>("Access denied: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Handles all other generic exceptions not handled previously.
     * @param ex The Exception.
     * @return An HTTP response with a generic error message and status 500 (INTERNAL_SERVER_ERROR).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Generic internal error: ", ex);
        return new ResponseEntity<>("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles the custom exception OrderNotFoundException.
     * @param ex The OrderNotFoundException.
     * @return An HTTP response with the exception message and status 404 (NOT_FOUND).
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
        logger.warn("Order not found: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the custom exception OrderInvalidStateException.
     * @param ex The OrderInvalidStateException.
     * @return An HTTP response with the exception message and status 400 (BAD_REQUEST).
     */
    @ExceptionHandler(OrderInvalidStateException.class)
    public ResponseEntity<String> handleOrderInvalidStateException(OrderInvalidStateException ex) {
        logger.warn("Invalid order state: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the custom exception OrderAlreadyExistsException.
     * @param ex The OrderAlreadyExistsException.
     * @return An HTTP response with the exception message and status 400 (BAD_REQUEST).
     */
    @ExceptionHandler(OrderAlreadyExistsException.class)
    public ResponseEntity<String> handleOrderAlreadyExistsException(OrderAlreadyExistsException ex) {
        logger.warn("Order already exists: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the custom exception OrderAlreadyInPreparationException.
     * @param ex The OrderAlreadyInPreparationException.
     * @return An HTTP response with the exception message and status 400 (BAD_REQUEST).
     */
    @ExceptionHandler(OrderAlreadyInPreparationException.class)
    public ResponseEntity<String> handleOrderAlreadyInPreparationException(OrderAlreadyInPreparationException ex) {
        logger.warn("Order already in preparation: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
