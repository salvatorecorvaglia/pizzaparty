package com.pizzaparty.repository;

import com.pizzaparty.entities.Order;
import com.pizzaparty.enumeration.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository that manages data access for orders in the Pizza Party order management system.
 * Extends JpaRepository to take advantage of default CRUD operations.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Finds an order by its unique code.
     * Returns an Optional to handle the case where the order does not exist.
     *
     * @param orderCode The unique identifier of the order.
     * @return An Optional that may contain the found order, or be empty if the order does not exist.
     */
    Optional<Order> findByOrderCode(String orderCode);

    /**
     * Finds all orders that have a specific status.
     *
     * @param status The status of the order (e.g., "WAITING", "PREPARATION", "READY").
     * @return A list of orders with the specified status.
     */
    List<Order> findByStatus(OrderStatusEnum status);

    /**
     * Checks if an order with the specified unique code exists.
     *
     * @param orderCode The unique identifier of the order.
     * @return true if at least one order with the code exists, otherwise false.
     */
    boolean existsByOrderCode(String orderCode);

    /**
     * Counts the number of orders that are currently in "PREPARATION" status.
     *
     * @param status The status of the order (PREPARATION).
     * @return The number of orders in preparation.
     */
    int countByStatus(OrderStatusEnum status);

}
