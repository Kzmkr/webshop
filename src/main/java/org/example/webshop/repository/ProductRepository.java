package org.example.webshop.repository;

import java.util.UUID;

import org.example.webshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Repository interface for managing {@link Product} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query methods.
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {
}