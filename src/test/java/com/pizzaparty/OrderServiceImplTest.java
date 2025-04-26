package com.pizzaparty;

import com.pizzaparty.dto.OrderDTO;
import com.pizzaparty.entities.Order;
import com.pizzaparty.enumeration.OrderStatusEnum;
import com.pizzaparty.exception.OrderInvalidStateException;
import com.pizzaparty.exception.OrderNotFoundException;
import com.pizzaparty.mapper.OrderMapper;
import com.pizzaparty.repository.OrderRepository;
import com.pizzaparty.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        order = new Order("Pizza Margherita"); // Sample order
    }

    @Test
    void testCreateOrder() {
        // Simulate that the code does not yet exist in the database
        when(orderRepository.existsByOrderCode(anyString())).thenReturn(false);

        // Simulate the repository behavior to save the order
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId(1L); // Simulate ID assignment from the database
            return savedOrder;
        });

        // Simulate the conversion of the order into DTO
        when(orderMapper.toDTO(any(Order.class))).thenAnswer(invocation -> {
            Order orderArg = invocation.getArgument(0);
            return new OrderDTO(orderArg.getId(), orderArg.getOrderCode(), orderArg.getDescription(), OrderStatusEnum.WAITING);
        });

        // Run the test
        OrderDTO result = orderService.createOrder("Pizza Margherita");

        // Verify the results
        assertEquals("Pizza Margherita", result.getDescription());
        assertEquals(OrderStatusEnum.WAITING, result.getStatus());
        assertNotNull(result.getOrderCode()); // The order code must be generated
        assertNotNull(result.getId()); // The ID must be generated

        // Verify that the existsByOrderCode method was called
        verify(orderRepository, atLeastOnce()).existsByOrderCode(anyString());

        // Verify that the order was saved and converted into DTO
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderMapper, times(1)).toDTO(any(Order.class));
    }

    @Test
    void testTakeCharge() {
        // Prepare the mock behavior of the repository and mapper
        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getOrderCode(), order.getDescription(), OrderStatusEnum.PREPARATION);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));  // Simulate retrieving the order
        when(orderRepository.save(any(Order.class))).thenReturn(order);  // Simulate saving the updated order
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);  // Simulate the conversion of the order into DTO

        // Run the test
        OrderDTO result = orderService.takeCharge(1L);

        // Verify the results
        assertNotNull(result);
        assertEquals(OrderStatusEnum.PREPARATION, result.getStatus());
        assertEquals("Pizza Margherita", result.getDescription());

        verify(orderRepository, times(1)).findById(1L);  // Verify retrieving the order
        verify(orderRepository, times(1)).save(any(Order.class));  // Verify saving the updated order
    }

    @Test
    void testCompleteOrder() {
        // Prepare the mock behavior of the repository and mapper
        order.setStatus(OrderStatusEnum.PREPARATION);  // Simulate the order being in preparation
        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getOrderCode(), order.getDescription(), OrderStatusEnum.READY);

        // Simulate retrieving the order with ID 1 and status IN_PREPARATION
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Simulate saving the order with status READY
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Simulate converting the order into DTO
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);

        // Run the test
        OrderDTO result = orderService.completeOrder(1L);

        // Verify the results
        assertNotNull(result);
        assertEquals(OrderStatusEnum.READY, result.getStatus());  // The status must be READY
        assertEquals("Pizza Margherita", result.getDescription());

        // Verify interactions with the repository and the mapper
        verify(orderRepository, times(1)).findById(1L);  // Verify retrieving the order
        verify(orderRepository, times(1)).save(any(Order.class));  // Verify saving the updated order
    }

    @Test
    void testGetOrderByCode() {
        // Prepare the mock behavior of the repository and mapper
        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getOrderCode(), order.getDescription(), OrderStatusEnum.WAITING);
        when(orderRepository.findByOrderCode("COD-21032025-0001")).thenReturn(Optional.of(order));  // Simulate retrieving the order by code
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);  // Simulate converting the order into DTO

        // Run the test
        OrderDTO result = orderService.getOrderByCode("COD-21032025-0001");

        // Verify the results
        assertNotNull(result);
        assertEquals("Pizza Margherita", result.getDescription());
        assertEquals(OrderStatusEnum.WAITING, result.getStatus());

        verify(orderRepository, times(1)).findByOrderCode("COD-21032025-0001");  // Verify retrieving the order by code
    }

    @Test
    void testGetOrderByCodeNotFound() {
        // Simulate the case where the order does not exist
        when(orderRepository.findByOrderCode("COD-21032025-0001")).thenReturn(Optional.empty());

        // Run the test and verify that an exception is thrown
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderByCode("COD-21032025-0001"));
    }

    @Test
    void testTakeChargeInvalidState() {
        // Simulate an order already taken (status IN_PREPARATION)
        order.setStatus(OrderStatusEnum.PREPARATION);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));  // Simulate retrieving the order

        // Verify that an exception is thrown when attempting to take charge
        assertThrows(OrderInvalidStateException.class, () -> orderService.takeCharge(1L));
    }

    @Test
    void testCreateOrderWithUniqueCode() {
        // Simulate repository behavior to avoid conflicts
        when(orderRepository.existsByOrderCode(anyString())).thenReturn(false);  // Ensure that the code does not already exist

        // Simulate the save behavior and the mapper
        Order order = new Order("Pizza Margherita");
        order.setId(1L);  // Simulate ID assignment
        order.setOrderCode("COD-21032025-0001");

        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getOrderCode(), order.getDescription(), OrderStatusEnum.WAITING);

        // Simulate repository and mapper behavior
        when(orderRepository.save(any(Order.class))).thenReturn(order);  // Save the order
        when(orderMapper.toDTO(any(Order.class))).thenReturn(orderDTO);  // Map the order to DTO

        // Prepare the order
        OrderDTO result = orderService.createOrder("Pizza Margherita");

        // Verify that the generated order code has the correct format
        assertNotNull(result.getOrderCode());
        assertTrue(result.getOrderCode().startsWith("COD"));  // The prefix must be "COD"
        assertTrue(result.getOrderCode().matches("COD-\\d{8}-\\d{4}"));  // Date and counter must follow the correct format

        // Verify that save and toDTO were called
        verify(orderRepository, times(1)).save(any(Order.class));  // Verify that save was called
        verify(orderMapper, times(1)).toDTO(any(Order.class));  // Verify that toDTO was called
    }
}
