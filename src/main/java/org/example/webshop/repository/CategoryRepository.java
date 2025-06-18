package org.example.webshop.repository;

import java.util.UUID;

import org.example.webshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Repository interface for managing {@link Category} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query methods.
 */
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}