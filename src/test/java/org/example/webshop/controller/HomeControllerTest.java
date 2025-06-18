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
    void homeReturnsHomeViewWithValidData() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        List<Product> products = Arrays.asList(new Product(), new Product());
        Page<Product> pagedProducts = new PageImpl<>(products);

        when(categoryService.getAll()).thenReturn(categories);
        when(productService.getFilteredProducts("", 0, 3, "price", "DESC", 0.f, 9999999999999.9f)).thenReturn(pagedProducts);

        String viewName = homeController.home(model, 1, "", 0.f, 9999999999999.9f);

        assertNotNull(viewName);
        assertEquals("home", viewName);
        verify(model).addAttribute("categories", categories);
        verify(model).addAttribute("products", pagedProducts);
        verify(model).addAttribute("totalPages", pagedProducts.getTotalPages());
    }

    @Test
    void homeHandlesEmptyProductList() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        Page<Product> emptyPagedProducts = new PageImpl<>(List.of());

        when(categoryService.getAll()).thenReturn(categories);
        when(productService.getFilteredProducts("", 0, 3, "price", "DESC", 0.f, 9999999999999.9f)).thenReturn(emptyPagedProducts);

        String viewName = homeController.home(model, 1, "", 0.f, 9999999999999.9f);

        assertNotNull(viewName);
        assertEquals("home", viewName);
        verify(model).addAttribute("categories", categories);
        verify(model).addAttribute("products", emptyPagedProducts);
        verify(model).addAttribute("totalPages", emptyPagedProducts.getTotalPages());
    }



    @Test
    void homeHandlesExtremePriceRange() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        List<Product> products = Arrays.asList(new Product(), new Product());
        Page<Product> pagedProducts = new PageImpl<>(products);

        when(categoryService.getAll()).thenReturn(categories);
        when(productService.getFilteredProducts("", 0, 3, "price", "DESC", Float.MIN_VALUE, Float.MAX_VALUE)).thenReturn(pagedProducts);

        String viewName = homeController.home(model, 1, "", Float.MIN_VALUE, Float.MAX_VALUE);

        assertNotNull(viewName);
        assertEquals("home", viewName);
        verify(model).addAttribute("categories", categories);
        verify(model).addAttribute("products", pagedProducts);
        verify(model).addAttribute("totalPages", pagedProducts.getTotalPages());
    }
}