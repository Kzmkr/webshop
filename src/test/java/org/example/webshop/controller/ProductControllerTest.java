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
import java.util.UUID;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @Mock
    private MultipartFile imageFile;

    @InjectMocks
    private ProductController productController;

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
        String viewName = productController.showUploadForm(model);

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
        product.setId(UUID.randomUUID()); // Set a valid ID for the product

        // Act
        String redirectUrl = productController.handleUploadForm(product, imageFile);

        // Assert
        assertNotNull(redirectUrl);
        assertEquals("redirect:/prod/" + product.getId(), redirectUrl); // Match the expected redirect URL
        verify(productService, times(1)).save(product, imageFile);
    }
    @Test
    void handleUploadFormRedirectsToProductDetailsWhenProductIsSaved() throws IOException {
        // Arrange
        Product product = new Product();
        product.setId(UUID.randomUUID());
        doNothing().when(productService).save(product, imageFile);

        // Act
        String redirectUrl = productController.handleUploadForm(product, imageFile);

        // Assert
        assertEquals("redirect:/prod/" + product.getId(), redirectUrl);
        verify(productService, times(1)).save(product, imageFile);
    }



    @Test
    void showProductDetailsAddsProductToModelWhenProductExists() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        when(productService.getById(productId)).thenReturn(product);

        // Act
        String viewName = productController.showProductDetails(productId, model);

        // Assert
        assertEquals("prod", viewName);
        verify(model, times(1)).addAttribute("product", product);
    }
    @Test
    void handleCategoryUploadAddsCategoryWithParent() {
        // Arrange
        String newCategoryName = "Electronics";
        String parentCategoryId = UUID.randomUUID().toString();
        Category parentCategory = new Category();
        parentCategory.setId(UUID.fromString(parentCategoryId));
        when(categoryService.getById(UUID.fromString(parentCategoryId))).thenReturn(parentCategory);

        // Act
        String redirectUrl = productController.handleCategoryUpload(newCategoryName, parentCategoryId, model);

        // Assert
        assertEquals("redirect:upload", redirectUrl);
        verify(categoryService, times(1)).save(argThat(category ->
                category.getName().equals(newCategoryName) && category.getParent() == parentCategory));
        verify(model, times(1)).addAttribute("successMessage", "Category uploaded successfully!");
    }

    @Test
    void handleCategoryUploadAddsCategoryWithoutParent() {
        // Arrange
        String newCategoryName = "Books";

        // Act
        String redirectUrl = productController.handleCategoryUpload(newCategoryName, null, model);

        // Assert
        assertEquals("redirect:upload", redirectUrl);
        verify(categoryService, times(1)).save(argThat(category ->
                category.getName().equals(newCategoryName) && category.getParent() == null));
        verify(model, times(1)).addAttribute("successMessage", "Category uploaded successfully!");
    }
    @Test
    void showUpdateFormDisplaysProductAndCategories() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(productService.getById(productId)).thenReturn(product);
        when(categoryService.getAll()).thenReturn(categories);

        // Act
        String viewName = productController.showUpdateForm(productId, model);

        // Assert
        assertEquals("edit", viewName);
        verify(productService, times(1)).getById(productId);
        verify(categoryService, times(1)).getAll();
        verify(model, times(1)).addAttribute("product", product);
        verify(model, times(1)).addAttribute("categories", categories);
    }

    @Test
    void handleUpdateFormUpdatesExistingProduct() throws IOException {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Name");
        updatedProduct.setPrice(99.99);
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setCategory(new Category());
        when(productService.getById(productId)).thenReturn(existingProduct);
        doNothing().when(productService).save(existingProduct, imageFile);

        // Act
        String redirectUrl = productController.handleUpdateForm(productId, updatedProduct, imageFile);

        // Assert
        assertEquals("redirect:/prod/" + productId, redirectUrl);
        assertEquals("Updated Name", existingProduct.getName());
        assertEquals(99.99, existingProduct.getPrice());
        assertEquals("Updated Description", existingProduct.getDescription());
        assertEquals(updatedProduct.getCategory(), existingProduct.getCategory());
        verify(productService, times(1)).getById(productId);
        verify(productService, times(1)).save(existingProduct, imageFile);
    }

    @Test
    void handleUpdateFormDoesNothingWhenProductDoesNotExist() throws IOException {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product updatedProduct = new Product();
        when(productService.getById(productId)).thenReturn(null);

        // Act
        String redirectUrl = productController.handleUpdateForm(productId, updatedProduct, imageFile);

        // Assert
        assertEquals("redirect:/prod/" + productId, redirectUrl);
        verify(productService, times(1)).getById(productId);
        verify(productService, never()).save(any(Product.class), eq(imageFile));
    }


}