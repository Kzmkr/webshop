package org.example.webshop.service;

import java.util.List;
import java.util.UUID;

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
    public Category getById(UUID id) {
        return categoryRepository.findById(id).orElse(null);
    }

    /**
     * Saves the given {@link Category} entity to the repository.
     *
     * @param category the {@link Category} entity to save
     */
    public void save(Category category) {
            categoryRepository.save(category);
    }
}