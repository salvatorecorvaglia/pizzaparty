package com.pizzaparty.dto;

import com.pizzaparty.enumeration.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO that represents a pizza order.
 * This object is used to transfer data between different layers of the application.
 */
@Data
@AllArgsConstructor
public class OrderDTO {

    private Long id; // Unique identifier of the order

    private String orderCode; // Unique code representing the order

    private String description; // Description of the order

    private OrderStatusEnum status; // Current status of the order
}
