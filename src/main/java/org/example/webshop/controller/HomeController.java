package org.example.webshop.controller;

import java.util.List;

import org.example.webshop.model.Category;
import org.example.webshop.model.Product;
import org.example.webshop.service.CategoryService;
import org.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



/**
 * Controller class for handling requests to the home page of the webshop.
 * Retrieves categories and products to display on the home page.
 */
@Controller
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;

    /**
     * Constructs a new {@code HomeController} with the specified services.
     *
     * @param productService the service used to manage products
     * @param categoryService the service used to manage categories
     */
    @Autowired
    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    /**
     * Handles GET requests to the root URL ("/").
     * Retrieves all categories and products, adds them to the model, and returns the "home" view.
     *
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view to render ("home")
     */
    @GetMapping("/")
    public String login(Model model) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        return "home";
    }
}