package org.example.webshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;
}

