package org.example.webshop.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.UUID;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String houseNumber;


}
