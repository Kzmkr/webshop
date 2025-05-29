package org.example.webshop.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;



/**
 * Entity class representing an address in the webshop.
 * Contains address details such as street, city, state, country, zip code, and house number.
 */
@Entity
@Data
public class Address {

    /**
     * The unique identifier for the address.
     * Generated automatically using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The street name of the address.
     */
    private String street;

    /**
     * The city of the address.
     */
    private String city;

    /**
     * The state of the address.
     */
    private String state;

    /**
     * The country of the address.
     */
    private String country;

    /**
     * The zip code of the address.
     */
    private String zipCode;

    /**
     * The house number of the address.
     */
    private String houseNumber;
}