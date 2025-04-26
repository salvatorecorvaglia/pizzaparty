package com.pizzaparty.mapper;

import com.pizzaparty.dto.OrderDTO;
import com.pizzaparty.entities.Order;
import org.springframework.stereotype.Component;

/**
 * Mapper class that converts an Order entity into an OrderDTO.
 * Mappers are used to separate the conversion logic between domain entities and DTOs.
 */
@Component
public class OrderMapper {

    /**
     * Converts an Order object into an OrderDTO object.
     * This method maps the fields of the Order entity into the OrderDTO.
     *
     * @param order The Order object to map
     * @return The resulting OrderDTO object from the mapping
     */
    // Maps Order -> OrderDTO
    public OrderDTO toDTO(Order order) {
        // Creates a new OrderDTO using data from the Order object
        return new OrderDTO(
                order.getId(), // Maps the order ID
                order.getOrderCode(), // Maps the order code
                order.getDescription(), // Maps the order description
                order.getStatus() // Maps the order status
        );
    }
}
