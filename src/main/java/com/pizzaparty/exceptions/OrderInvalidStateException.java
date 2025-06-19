package com.pizzaparty.exceptions;

/**
 * Custom exception to handle situations where an order cannot be
 * processed because it is not in the correct state.
 * Extends RuntimeException, allowing it to be used without explicit handling.
 */
public class OrderInvalidStateException extends RuntimeException {

    /**
     * Constructor for the OrderInvalidStateException class.
     * This is called when the exception is thrown, accepting a message
     * that describes why the order is in an invalid state.
     *
     * @param message The message describing the error.
     */
    public OrderInvalidStateException(String message) {
        // Passes the message to the constructor of RuntimeException
        super(message);
    }
}
