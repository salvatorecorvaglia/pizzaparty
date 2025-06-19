package com.pizzaparty.exceptions;

/**
 * Exception thrown when trying to take charge of an order
 * but another order is already in preparation.
 */
public class OrderAlreadyInPreparationException extends RuntimeException {
    public OrderAlreadyInPreparationException(String message) {
        super(message);
    }
}
