package com.pizzaparty.service;

import com.pizzaparty.config.Utils;
import com.pizzaparty.dto.OrderDTO;
import com.pizzaparty.entity.Order;
import com.pizzaparty.enumeration.OrderStatusEnum;
import com.pizzaparty.exceptions.OrderAlreadyExistsException;
import com.pizzaparty.exceptions.OrderAlreadyInPreparationException;
import com.pizzaparty.exceptions.OrderInvalidStateException;
import com.pizzaparty.exceptions.OrderNotFoundException;
import com.pizzaparty.mapper.OrderMapper;
import com.pizzaparty.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the service for managing orders in the Pizza Party order management system.
 * Provides methods for creating, updating, and retrieving orders.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    /**
     * Creates a new order with a description, generating a unique order code
     * based on the current date and a counter. If the date changes, the counter is reset.
     * Verifies that the generated order code does not already exist in the system.
     *
     * @param description The description of the order to create.
     * @return The DTO of the newly created order.
     * @throws RuntimeException If the generated order code already exists (condition handled by do-while loop).
     */
    @Override
    @Transactional
    public OrderDTO createOrder(String description) {

        // Get the current date in ddMMyyyy format
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        // Get the last date and counter from the state variable, if available
        String counter = "0001";  // Define a starting value for the counter
        String lastDate = "";  // Variable to store the last date

        // If the current date is different from the last date, reset the counter
        if (!currentDate.equals(lastDate)) {
            counter = "0001";  // Reset the counter
            lastDate = currentDate;  // Update the last date
        }

        String orderCode;
        do {
            // Generate the order code using Utils
            orderCode = Utils.generateOrderCode(lastDate, counter);

            // Increment the counter
            counter = Utils.increaseCounter(counter);

        } while (orderRepository.existsByOrderCode(orderCode));  // Check if the code already exists

        // Verify if the order already exists
        if (orderRepository.existsByOrderCode(orderCode)) {
            throw new OrderAlreadyExistsException("Order with code " + orderCode + " already exists. Cannot create a duplicate order.");
        }
        // Create and save the order
        Order order = new Order(description);
        order.setOrderCode(orderCode);
        orderRepository.save(order);

        return orderMapper.toDTO(order);  // Return the DTO of the newly created order
    }

    /**
     * Takes charge of an order identified by its ID, updating its status to "IN_PREPARATION".
     *
     * @param id The ID of the order to take charge of.
     * @return The DTO of the updated order.
     * @throws RuntimeException If the order does not exist.
     */
    @Override
    @Transactional
    public OrderDTO takeCharge(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));  // Find the order or throw an exception

        // Verify that the order's status is "WAITING"
        if (order.getStatus() != OrderStatusEnum.WAITING) {
            // If the order is not in "WAITING" status, throw the OrderInvalidStateException
            throw new OrderInvalidStateException("Order with ID " + id + " cannot be taken charge of. Current status: " + order.getStatus());
        }

        // Count orders in preparation
        if (orderRepository.countByStatus(OrderStatusEnum.PREPARATION) >= 1) {
            throw new OrderAlreadyInPreparationException("There is already an order in preparation. Complete that one first.");
        }

        order.setStatus(OrderStatusEnum.PREPARATION);  // Set the status to "IN_PREPARATION"
        Order savedOrder = orderRepository.save(order);  // Save the updated order
        return orderMapper.toDTO(savedOrder);  // Return the DTO of the updated order
    }

    /**
     * Completes an order identified by its ID, updating its status to "READY".
     *
     * @param id The ID of the order to complete.
     * @return The DTO of the completed order.
     * @throws RuntimeException If the order does not exist.
     */
    @Override
    @Transactional
    public OrderDTO completeOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));  // Find the order or throw an exception

        // The order can only be completed if it is in the "IN_PREPARATION" state
        if (order.getStatus() != OrderStatusEnum.PREPARATION) {
            throw new OrderInvalidStateException("Order with ID " + id + " cannot be completed. Current status: " + order.getStatus());
        }

        order.setStatus(OrderStatusEnum.READY);  // Set the status to "READY"
        Order savedOrder = orderRepository.save(order);  // Save the updated order
        return orderMapper.toDTO(savedOrder);  // Return the DTO of the completed order
    }

    /**
     * Retrieves all orders that are waiting to be taken charge of.
     *
     * @return A list of DTOs of orders in the "WAITING" status.
     */
    @Override
    public List<OrderDTO> getPendingOrders() {
        List<Order> orders = orderRepository.findByStatus(OrderStatusEnum.WAITING);  // Find orders in the "WAITING" status
        return orders.stream()
                .map(orderMapper::toDTO)  // Map each order to a DTO
                .collect(Collectors.toList());  // Collect the DTOs in a list
    }

    /**
     * Retrieves an order by its unique order code.
     *
     * @param code The unique order code.
     * @return The DTO of the order corresponding to the code.
     * @throws RuntimeException If the order is not found.
     */
    @Override
    public OrderDTO getOrderByCode(String code) {
        Order order = orderRepository.findByOrderCode(code)
                .orElseThrow(() -> new OrderNotFoundException("Order with code " + code + " not found"));  // Find the order or throw an exception
        return orderMapper.toDTO(order);  // Return the DTO of the found order
    }
}
