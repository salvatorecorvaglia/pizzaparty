package com.pizzaparty.exceptions;

/**
 * Custom exception to handle the case when an order is not found in the system.
 * Extends RuntimeException, allowing it to be used without explicit handling.
 */
public class OrderNotFoundException extends RuntimeException {

    /**
     * Constructor for the OrderNotFoundException class.
     * This constructor is used to create an instance of this exception
     * when the condition occurs where an order is not found.
     * The passed message will be provided as the error detail.
     *
     * @param message The message describing why the order was not found.
     */
    public OrderNotFoundException(String message) {
        // Passes the error message to the superclass RuntimeException
        super(message);
    }
}
