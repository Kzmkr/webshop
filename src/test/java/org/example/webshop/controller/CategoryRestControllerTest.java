package org.example.webshop.controller;

import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

        import org.example.webshop.model.Category;
import org.example.webshop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

class CategoryRestControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryRestController categoryRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryService.getAll()).thenReturn(categories);

        // Act
        List<Category> result = categoryRestController.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryService, times(1)).getAll();
    }

    @Test
    void testGetCategoryById() {
        // Arrange
        int id = 1;
        Category category = new Category();
        when(categoryService.getById(id)).thenReturn(category);

        // Act
        Category result = categoryRestController.getCategoryById(id);

        // Assert
        assertNotNull(result);
        assertEquals(category, result);
        verify(categoryService, times(1)).getById(id);
    }
}