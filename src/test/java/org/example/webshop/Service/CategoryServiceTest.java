package org.example.webshop.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.webshop.model.Category;
import org.example.webshop.repository.CategoryRepository;
import org.example.webshop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    void retrievesAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }
    @Test
    void retrievesCategoryByIdWhenExists() {
        Category category = new Category();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Category result = categoryService.getById(1);

        assertNotNull(result);
        verify(categoryRepository, times(1)).findById(1);
    }
    @Test
    void returnsNullWhenCategoryDoesNotExist() {
        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        Category result = categoryService.getById(999);

        assertNull(result);
        verify(categoryRepository, times(1)).findById(999);
    }
    @Test
    void retrievesEmptyListWhenNoCategoriesExist() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());

        List<Category> result = categoryService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(categoryRepository, times(1)).findAll();
    }
}