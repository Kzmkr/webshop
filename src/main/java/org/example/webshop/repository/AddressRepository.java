package org.example.webshop.repository;

import java.util.UUID;

import org.example.webshop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Repository interface for managing Address entities.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
}
