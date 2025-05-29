package org.example.webshop.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;



/**
 * Entity class representing a user in the webshop.
 * Contains user details such as name, email, password, and associated address.
 */
@Entity
@Data
public class User {

    /**
     * The unique identifier for the user.
     * Generated automatically using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The address associated with the user.
     * Mapped as a one-to-one relationship.
     */
    @OneToOne
    @JoinColumn(name = "addressID")
    private Address address;
}