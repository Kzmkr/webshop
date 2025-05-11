package org.example.webshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.rmi.server.UID;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UID id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String houseNumber;
}
