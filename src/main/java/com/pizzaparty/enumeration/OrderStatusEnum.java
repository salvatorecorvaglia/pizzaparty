package com.pizzaparty.enumeration;

/**
 * Enum representing the different statuses of an order.
 */
public enum OrderStatusEnum {

    // Definition of order statuses with their description
    WAITING("Waiting"), // Status where the order has been received and is not yet in preparation
    PREPARATION("Preparation"), // Status where the order is currently being prepared
    READY("Ready"); // Status where the order has been completed and is ready for pickup

    // Private variable to store the description of the status
    private final String description;

    // Constructor that assigns the description to each order status
    OrderStatusEnum(String description) {
        this.description = description; // Assigns the description passed to the constructor
    }

    // Overridden toString() method to return a readable representation of the status
    @Override
    public String toString() {
        // Returns the enum name along with its description
        return name() + " - " + description; // Combines the enum name with its description
    }
}
