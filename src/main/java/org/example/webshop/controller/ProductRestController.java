package org.example.webshop.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.example.webshop.model.Product;
import org.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing products in the webshop.
 * Provides endpoints to retrieve, upload, and manage product data.
 */
@RestController
@RequestMapping("/products")
public class ProductRestController {

    private final ProductService productService;

    /**
     * Constructs a new {@code ProductRestController} with the specified {@code ProductService}.
     *
     * @param productService the service used to manage products
     */
    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves a list of all products.
     *
     * @return a list of {@link Product} objects
     */
    @GetMapping("")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    /**
     * Retrieves a specific product by its ID.
     *
     * @param id the UUID of the product to retrieve
     * @return the {@link Product} object with the specified ID
     */
    @GetMapping("/{id}")
    public Product getTeacherProfile(@PathVariable UUID id) {
        return productService.getById(id);
    }


    /**
     * Uploads a new product along with its associated image file.
     *
     * @param product   the {@link Product} object to upload
     * @param imageFile the image file associated with the product
     * @return the uploaded {@link Product} object
     * @throws IOException if an error occurs while processing the image file
     */
    @PostMapping("")
    public Product uploadProduct(@RequestPart("product") Product product,
                                 @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        productService.save(product, imageFile);
        return product;
    }
}