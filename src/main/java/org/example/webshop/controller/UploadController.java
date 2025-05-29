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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller class for handling product upload functionality.
 * Provides endpoints to display the upload form and handle product uploads.
 */
@Controller
public class UploadController {

    private final ProductService productService;
    private final CategoryService categoryService;

    /**
     * Constructs a new {@code UploadController} with the specified services.
     *
     * @param productService the service used to manage products
     * @param categoryService the service used to manage categories
     */
    @Autowired
    public UploadController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    /**
     * Handles GET requests to the "/upload" endpoint.
     * Displays the upload form with a list of categories and an empty product object.
     *
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view to render ("upload")
     */
    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "upload";
    }

    /**
     * Handles POST requests to the "/upload" endpoint.
     * Processes the uploaded product and its associated image file.
     * Redirects to the upload page with a success message upon successful upload.
     *
     * @param product   the {@link Product} object containing the product details
     * @param imageFile the image file associated with the product
     * @return a redirect to the upload page with a success query parameter
     */
    @PostMapping("/upload")
    public String handleUploadForm(@ModelAttribute Product product, @RequestParam("image") MultipartFile imageFile) {
        try {
            productService.save(product, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/upload?success";
    }
}