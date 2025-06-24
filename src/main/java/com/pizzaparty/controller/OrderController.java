package com.pizzaparty.controller;

import com.pizzaparty.dto.OrderDTO;
import com.pizzaparty.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller that handles HTTP requests related to orders.
 * This class provides endpoints to create, update, and retrieve orders.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates a new order.
     * This endpoint receives the order description and uses it to create a new order.
     *
     * @param description Description of the order to be created
     * @return OrderDTO representing the newly created order
     */
    @PostMapping // Endpoint to create a new order
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam String description) {
        // Calls the service to create the order and returns the created order DTO
        OrderDTO orderDTO = orderService.createOrder(description);
        return ResponseEntity.ok(orderDTO); // Responds with HTTP status 200 and the order DTO
    }

    /**
     * Retrieves a list of orders that are still waiting to be processed.
     *
     * @return List of pending orders
     */
    @GetMapping("/waiting") // Endpoint to retrieve pending orders
    @PreAuthorize("hasAnyRole('PIZZAIOLO', 'ADMIN')")
    public ResponseEntity<List<OrderDTO>> getPendingOrders() {
        // Calls the service to get the pending orders and returns the list
        List<OrderDTO> orders = orderService.getPendingOrders();
        return ResponseEntity.ok(orders); // Responds with HTTP status 200 and the list of orders
    }

    /**
     * Sets an order as "taken in charge" by the pizzaiolo (taken into processing).
     *
     * @param id ID of the order to take in charge
     * @return OrderDTO representing the taken-in-charge order
     */
    @PutMapping("/{id}/take-charge") // Endpoint to take an order in charge
    @PreAuthorize("hasRole('PIZZAIOLO')")
    public ResponseEntity<OrderDTO> takeCharge(@PathVariable Long id) {
        // Calls the service to take the order in charge and returns the updated DTO
        OrderDTO orderDTO = orderService.takeCharge(id);
        return ResponseEntity.ok(orderDTO); // Responds with HTTP status 200 and the updated order DTO
    }

    /**
     * Completes an order, marking the end of the preparation process.
     *
     * @param id ID of the order to complete
     * @return OrderDTO representing the completed order
     */
    @PutMapping("/{id}/complete") // Endpoint to complete an order
    @PreAuthorize("hasRole('PIZZAIOLO')")
    public ResponseEntity<OrderDTO> completeOrder(@PathVariable Long id) {
        // Calls the service to complete the order and returns the updated DTO
        OrderDTO orderDTO = orderService.completeOrder(id);
        return ResponseEntity.ok(orderDTO); // Responds with HTTP status 200 and the completed order DTO
    }

    /**
     * Retrieves a specific order based on its order code.
     *
     * @param orderCode Unique code identifying the order
     * @return OrderDTO representing the order corresponding to the provided code
     */
    @GetMapping("/{orderCode}") // Endpoint to retrieve a specific order by code
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable String orderCode) {
        // Calls the service to retrieve the order by code and returns the DTO
        OrderDTO orderDTO = orderService.getOrderByCode(orderCode);
        return ResponseEntity.ok(orderDTO); // Responds with HTTP status 200 and the order DTO
    }
}
