package org.example.webshop.repository;

import java.util.Optional;
import java.util.UUID;

import org.example.webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Repository interface for managing {@link User} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query methods.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to retrieve
     * @return an {@link Optional} containing the {@link User} if found, or empty if not found
     */
    Optional<User> findByEmail(String email);
}