package com.pizzaparty.service;

import com.pizzaparty.dto.OrderDTO;

import java.util.List;

/**
 * Interface that defines the methods for managing orders in the Pizza Party order management system.
 */
public interface OrderService {

    /**
     * Creates a new order with a description and returns the order's DTO.
     *
     * @param description The description of the order to be created.
     * @return The DTO representing the created order.
     */
    OrderDTO createOrder(String description);

    /**
     * Takes charge of an order identified by a specific ID and returns the updated order's DTO.
     *
     * @param id The ID of the order to be taken in charge.
     * @return The DTO of the order updated after being taken in charge.
     */
    OrderDTO takeCharge(Long id);

    /**
     * Completes an order identified by a specific ID and returns the completed order's DTO.
     *
     * @param id The ID of the order to be completed.
     * @return The DTO of the order after it has been completed.
     */
    OrderDTO completeOrder(Long id);

    /**
     * Returns a list of orders that are pending to be processed, represented as a list of DTOs.
     *
     * @return A list of DTOs of orders that are still pending to be managed.
     */
    List<OrderDTO> getPendingOrders();

    /**
     * Returns an order given its identification code as a DTO.
     *
     * @param code The identification code of the order to be retrieved.
     * @return The DTO of the order corresponding to the provided code.
     */
    OrderDTO getOrderByCode(String code);
}
