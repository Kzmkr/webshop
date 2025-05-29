package org.example.webshop.service;

import java.util.List;

import org.example.webshop.model.Category;
import org.example.webshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing {@link Category} entities.
 * Provides methods to retrieve all categories or a specific category by its ID.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Constructs a new {@code CategoryService} with the specified {@link CategoryRepository}.
     *
     * @param categoryRepository the repository used for category data access
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all categories from the repository.
     *
     * @return a list of all {@link Category} entities
     */
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return the {@link Category} entity with the specified ID, or {@code null} if not found
     */
    public Category getById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }
}