package org.example.webshop.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Entity class representing a product in the webshop.
 * Contains product details such as name, description, price, image URL, and associated category.
 */
@Entity
@Data
public class Product {

    /**
     * The unique identifier for the product.
     * Generated automatically using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * A brief description of the product.
     */
    private String description;

    /**
     * The price of the product.
     */
    private double price;

    /**
     * The URL of the product's image.
     */
    private String imageUrl;

    /**
     * The category associated with the product.
     * Mapped as a many-to-one relationship.
     */
    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;
}