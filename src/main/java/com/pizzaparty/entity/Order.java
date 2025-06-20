package com.pizzaparty.entity;

import com.pizzaparty.enumeration.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing an order in the Pizza Party order management system.
 * This entity is mapped to the "Ordine" table in the database.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /**
     * Unique identifier of the order, automatically generated by the database.
     */
    @Id // Indicates that this field is the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatic value generation for the ID field using the Identity strategy
    private Long id;

    /**
     * Unique code of the order, automatically generated before saving.
     * Cannot be null and is not updatable after creation.
     */
    @Column(unique = true, nullable = false, updatable = false) // The column must be unique, not null, and not updatable
    private String orderCode;

    /**
     * Status of the order, represented through an enumeration.
     * By default, it is set to "WAITING".
     */
    @Enumerated(EnumType.STRING) // The column will be stored as a string representing the enum
    private OrderStatusEnum status = OrderStatusEnum.WAITING; // Initial status of the order (default is waiting)

    /**
     * Description of the order, with a maximum length of 255 characters.
     */
    @NotEmpty(message = "The description cannot be empty")
    @Size(max = 255, message = "The description cannot exceed 255 characters")
    private String description;

    /**
     * Custom constructor to create an order with only the description.
     * The order code will be generated automatically.
     *
     * @param description Description of the order.
     */
    public Order(String description) {
        this.description = description; // Initializes only the description
    }
}
