package org.example.webshop.specification;

import org.example.webshop.model.Product;
import org.springframework.data.jpa.domain.Specification;

/**
 * Provides static methods to create specifications for querying {@link Product} entities.
 * These specifications can be used to filter products based on various criteria such as name, category, and price range.
 */
public class ProductSpecification {

    /**
     * Creates a specification to filter products by name.
     * The filter performs a case-insensitive search for products whose names contain the specified substring.
     *
     * @param name the substring to search for in product names
     * @return a {@link Specification} to filter products by name
     */
    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    /**
     * Creates a specification to filter products by category.
     * The filter matches products that belong to the specified category.
     *
     * @param category the category to filter products by
     * @return a {@link Specification} to filter products by category
     */
    public static Specification<Product> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("name"), category);
    }

    /**
     * Creates a specification to filter products by price range.
     * The filter matches products whose prices fall between the specified minimum and maximum values (inclusive).
     *
     * @param low  the minimum price of the range
     * @param high the maximum price of the range
     * @return a {@link Specification} to filter products by price range
     */
    public static Specification<Product> betweenPrice(float low, float high) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), low, high);
    }
}