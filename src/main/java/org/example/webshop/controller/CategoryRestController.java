package org.example.webshop.controller;

import java.util.List;

import org.example.webshop.model.Category;
import org.example.webshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * REST controller for managing categories in the webshop.
 * Provides endpoints to retrieve all categories or a specific category by its ID.
 */
@RestController
@RequestMapping("/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    /**
     * Constructs a new {@code CategoryRestController} with the specified {@code CategoryService}.
     *
     * @param categoryService the service used to manage categories
     */
    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Retrieves a list of all categories.
     *
     * @return a list of {@link Category} objects
     */
    @GetMapping("")
    public List<Category> getAllProducts() {
        return categoryService.getAll();
    }

    /**
     * Retrieves a specific category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return the {@link Category} object with the specified ID
     */
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoryService.getById(id);
    }
}