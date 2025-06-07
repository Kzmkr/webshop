package org.example.webshop.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.webshop.model.Product;
import org.example.webshop.repository.ProductRepository;
import org.example.webshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.InputStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MultipartFile imageFile;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = productService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        // Arrange
        UUID id = UUID.randomUUID();
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals(product, result);
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void testSaveWithImageFile() throws IOException {
        // Arrange
        Product product = new Product();
        String originalFileName = "image.jpg";
        String generatedFileName = UUID.randomUUID() + "_" + originalFileName;

        when(imageFile.isEmpty()).thenReturn(false);
        when(imageFile.getOriginalFilename()).thenReturn(originalFileName);
        when(imageFile.getInputStream()).thenReturn(mock(InputStream.class)); // Mock InputStream

        // Act
        productService.save(product, imageFile);

        // Assert
        assertNotNull(product.getImageUrl());
        assertTrue(product.getImageUrl().contains("uploads/"));
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testSaveWithoutImageFile() throws IOException {
        // Arrange
        Product product = new Product();
        when(imageFile.isEmpty()).thenReturn(true);

        // Act
        productService.save(product, imageFile);

        // Assert
        assertNull(product.getImageUrl());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetPagedProducts() {
        // Arrange
        int page = 0;
        int size = 2;
        String sortBy = "price";
        String sortDirection = "DESC";

        List<Product> products = Arrays.asList(new Product(), new Product());
        Page<Product> mockPage = new PageImpl<>(products);

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        when(productRepository.findAll(pageable)).thenReturn(mockPage);

        // Act
        Page<Product> result = productService.getPagedProducts(page, size, sortBy, sortDirection);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(productRepository, times(1)).findAll(pageable);
    }
}