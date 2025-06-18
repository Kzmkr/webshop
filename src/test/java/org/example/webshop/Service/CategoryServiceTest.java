package org.example.webshop.Service;

import org.example.webshop.model.Category;
import org.example.webshop.repository.CategoryRepository;
import org.example.webshop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReturnsAllCategories() {
        // Arrange
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<Category> result = categoryService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getByIdReturnsCategoryIfExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        Category category = new Category();
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        // Act
        Category result = categoryService.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals(category, result);
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void getByIdReturnsNullIfNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Category result = categoryService.getById(id);

        // Assert
        assertNull(result);
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void saveCallsRepositorySave() {
        // Arrange
        Category category = new Category();

        // Act
        categoryService.save(category);

        // Assert
        verify(categoryRepository, times(1)).save(category);
    }
}