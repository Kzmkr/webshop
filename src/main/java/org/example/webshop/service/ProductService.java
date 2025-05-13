package org.example.webshop.service;

import org.example.webshop.model.Product;
import org.example.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    private static final String UPLOAD_DIR = "src/main/resources/uploads/";

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }
    public Product getById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    public void save(Product product, MultipartFile imageFile) throws IOException {
        if (! imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(imageFile.getInputStream(), filePath);
            product.setImageUrl("/" + fileName);
        }
        productRepository.save(product);
    }


}
