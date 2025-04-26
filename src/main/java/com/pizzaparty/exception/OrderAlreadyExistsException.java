package com.pizzaparty.exception;

/**
 * Custom exception thrown when trying to create an order with a code that already exists in the system.
 * Extends RuntimeException, allowing it to be used without explicit handling.
 */
public class OrderAlreadyExistsException extends RuntimeException {

    /**
     * Constructor that accepts an error message.
     *
     * @param message The error message describing why the order cannot be created.
     */
    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
