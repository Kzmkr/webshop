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
 * Entity class representing a category in the webshop.
 * Contains category details such as name and its parent category.
 */
@Entity
@Data
public class Category {

    /**
     * The unique identifier for the category.
     * Generated automatically using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the category.
     */
    private String name;

    /**
     * The parent category associated with this category.
     * Mapped as a many-to-one relationship.
     */
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Category parent;
}