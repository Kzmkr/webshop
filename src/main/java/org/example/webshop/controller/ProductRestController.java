package org.example.webshop.controller;

import org.example.webshop.model.Product;
import org.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/products")
public class ProductRestController {


    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getTeacherProfile(@PathVariable UUID id, Model model) {
        return  productService.getById(id);
    }


    @PostMapping("")
    public Product uploadProduct(@RequestPart("product") Product product, @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        productService.save(product, imageFile);
        return product;
    }


}
