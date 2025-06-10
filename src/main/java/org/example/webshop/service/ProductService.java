package org.example.webshop.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.example.webshop.model.Product;
import org.example.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service class for managing products in the webshop.
 * Provides methods to retrieve, save, and manage product data.
 */
@Service
public class ProductService {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private final ProductRepository productRepository;

    /**
     * Constructs a new {@code ProductService} with the specified {@code ProductRepository}.
     *
     * @param productRepository the repository used to manage product data
     */
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves a list of all products.
     *
     * @return a list of {@link Product} objects
     */
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a specific product by its ID.
     *
     * @param id the UUID of the product to retrieve
     * @return the {@link Product} object with the specified ID, or {@code null} if not found
     */
    public Product getById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Saves a product to the repository and uploads its associated image file.
     * If the image file is not empty, it is saved to the upload directory, and the product's image URL is updated.
     *
     * @param product   the {@link Product} object to save
     * @param imageFile the image file associated with the product
     * @throws IOException if an error occurs while saving the image file
     */
    public void save(Product product, MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(imageFile.getInputStream(), filePath);
            product.setImageUrl("uploads/" + fileName);
        }
        productRepository.save(product);
    }

    /**
     * Retrieves a paginated list of products sorted by the specified criteria.
     *
     * @param page         the page number to retrieve (zero-based index)
     * @param size         the number of products per page
     * @param sortBy       the field by which to sort the products (e.g., "price")
     * @param sortDirection the direction of sorting ("ASC" for ascending, "DESC" for descending)
     * @return a {@link Page} containing the paginated and sorted products
     */

    public Page<Product> getPagedProducts(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }
}