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
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class UploadControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @Mock
    private MultipartFile imageFile;

    @InjectMocks
    private UploadController uploadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowUploadForm() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryService.getAll()).thenReturn(categories);

        // Act
        String viewName = uploadController.showUploadForm(model);

        // Assert
        assertNotNull(viewName);
        assertEquals("upload", viewName);
        verify(categoryService, times(1)).getAll();
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
        verify(model, times(1)).addAttribute("categories", categories);
    }

    @Test
    void testHandleUploadForm() throws IOException {
        // Arrange
        Product product = new Product();

        // Act
        String redirectUrl = uploadController.handleUploadForm(product, imageFile);

        // Assert
        assertNotNull(redirectUrl);
        assertEquals("redirect:/upload?success", redirectUrl);
        verify(productService, times(1)).save(product, imageFile);
    }
}