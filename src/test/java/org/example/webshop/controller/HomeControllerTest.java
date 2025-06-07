package org.example.webshop.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.webshop.model.Category;
import org.example.webshop.model.Product;
import org.example.webshop.service.CategoryService;
import org.example.webshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

class HomeControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHome() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category(), new Category());
        List<Product> products = Arrays.asList(new Product(), new Product());
        Page<Product> pagedProducts = new PageImpl<>(products);

        when(categoryService.getAll()).thenReturn(categories);
        when(productService.getPagedProducts(0, 12, "price", "DESC")).thenReturn(pagedProducts);

        // Act
        String viewName = homeController.home(model, 1);

        // Assert
        assertNotNull(viewName);
        assertEquals("home", viewName);
        verify(categoryService, times(1)).getAll();
        verify(productService, times(1)).getPagedProducts(0, 12, "price", "DESC");
        verify(model, times(1)).addAttribute("categories", categories);
        verify(model, times(1)).addAttribute("products", pagedProducts);
        verify(model, times(1)).addAttribute("totalPages", pagedProducts.getTotalElements());
    }
}