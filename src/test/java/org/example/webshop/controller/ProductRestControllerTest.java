package org.example.webshop.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.webshop.model.Product;
import org.example.webshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class ProductRestControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private MultipartFile imageFile;

    @InjectMocks
    private ProductRestController productRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.getAll()).thenReturn(products);

        // Act
        List<Product> result = productRestController.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productService, times(1)).getAll();
    }

    @Test
    void testGetTeacherProfile() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        when(productService.getById(productId)).thenReturn(product);

        // Act
        Product result = productRestController.getTeacherProfile(productId);

        // Assert
        assertNotNull(result);
        verify(productService, times(1)).getById(productId);
    }


}