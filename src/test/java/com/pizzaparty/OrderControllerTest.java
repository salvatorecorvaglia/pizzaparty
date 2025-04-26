package com.pizzaparty;

import com.pizzaparty.controller.OrderController;
import com.pizzaparty.dto.OrderDTO;
import com.pizzaparty.enumeration.OrderStatusEnum;
import com.pizzaparty.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Create a sample OrderDTO object
        orderDTO = new OrderDTO(1L, "1234", "Pizza Margherita", OrderStatusEnum.WAITING);
        orderDTO.setId(1L);
        orderDTO.setDescription("Pizza Margherita");
    }

    @Test
    void testCreateOrder() {
        // Mocked behavior
        when(orderService.createOrder("Pizza Margherita")).thenReturn(orderDTO);

        // Call to the controller
        ResponseEntity<OrderDTO> response = orderController.createOrder("Pizza Margherita");

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Pizza Margherita", response.getBody().getDescription());
        verify(orderService, times(1)).createOrder("Pizza Margherita");
    }

    @Test
    void testGetPendingOrders() {
        // Mocked behavior
        List<OrderDTO> orders = Collections.singletonList(orderDTO);
        when(orderService.getPendingOrders()).thenReturn(orders);

        // Call to the controller
        ResponseEntity<List<OrderDTO>> response = orderController.getPendingOrders();

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        verify(orderService, times(1)).getPendingOrders();
    }

    @Test
    void testTakeCharge() {
        // Mocked behavior
        when(orderService.takeCharge(1L)).thenReturn(orderDTO);

        // Call to the controller
        ResponseEntity<OrderDTO> response = orderController.takeCharge(1L);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        verify(orderService, times(1)).takeCharge(1L);
    }

    @Test
    void testCompleteOrder() {
        // Mocked behavior
        when(orderService.completeOrder(1L)).thenReturn(orderDTO);

        // Call to the controller
        ResponseEntity<OrderDTO> response = orderController.completeOrder(1L);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        verify(orderService, times(1)).completeOrder(1L);
    }

    @Test
    void testGetOrder() {
        // Mocked behavior
        when(orderService.getOrderByCode("1234")).thenReturn(orderDTO);

        // Call to the controller
        ResponseEntity<OrderDTO> response = orderController.getOrder("1234");

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pizza Margherita", Objects.requireNonNull(response.getBody()).getDescription());
        verify(orderService, times(1)).getOrderByCode("1234");
    }
}
