package org.example.webshop.controller;

import java.util.List;
import java.util.UUID;

import org.example.webshop.model.Category;
import org.example.webshop.model.Product;
import org.example.webshop.service.CategoryService;
import org.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller class for handling product upload functionality.
 * Provides endpoints to display the upload form and handle product uploads.
 */
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    /**
     * Constructs a new {@code UploadController} with the specified services.
     *
     * @param productService the service used to manage products
     * @param categoryService the service used to manage categories
     */
    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
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
     * Handles GET requests to the "/add-category" endpoint.
     * Displays the form for adding a new category and processes the category upload.
     * If a parent category is specified, the new category is associated with it.
     * Otherwise, the new category is created as a top-level category.
     *
     * @param newCategoryName the name of the new category to be added (optional)
     * @param parentCategory  the ID of the parent category to associate with the new category (optional)
     * @param model           the {@link Model} object used to pass data to the view
     * @return a redirect to the upload page with a success message
     */

    @GetMapping("/add-category")
    public String handleCategoryUpload(@RequestParam(required = false) String newCategoryName,
                                       @RequestParam(required = false) String parentCategory,
                                       Model model) {
        Category category = new Category();
        category.setName(newCategoryName);


        if (parentCategory != null && !parentCategory.isEmpty()) {
            Category parent = categoryService.getById(UUID.fromString(parentCategory));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }


        categoryService.save(category);
        model.addAttribute("successMessage", "Category uploaded successfully!");
        return "redirect:upload";
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
        return "redirect:/prod/" + product.getId();
    }

    /**
     * Handles GET requests to the "/edit/{id}" endpoint.
     * Displays the form for editing an existing product.
     * Retrieves the product by its ID and passes it along with the list of categories to the view.
     *
     * @param id    the ID of the product to be edited
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view to render ("edit")
     */
    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Product product = productService.getById(id);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "edit";
    }
    /**
     * Handles POST requests to the "/edit/{id}" endpoint.
     * Processes the updated product details and associated image file.
     * Updates the existing product in the database and redirects to the product details page.
     *
     * @param id        the ID of the product to be updated
     * @param product   the {@link Product} object containing the updated product details
     * @param imageFile the image file associated with the product
     * @return a redirect to the product details page for the updated product
     */

    @PostMapping("edit/{id}")
    public String handleUpdateForm(@PathVariable("id") UUID id,
                                   @ModelAttribute Product product,
                                   @RequestParam("image") MultipartFile imageFile) {
        try {
            Product existingProduct = productService.getById(id);
            if (existingProduct != null) {
                existingProduct.setName(product.getName());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setDescription(product.getDescription());
                existingProduct.setCategory(product.getCategory());
                productService.save(existingProduct, imageFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/prod/" + id;
    }
    /**
     * Handles GET requests to the "/prod/{id}" endpoint.
     * Displays the details of a specific product.
     * Retrieves the product by its ID and passes it to the view.
     *
     * @param id    the ID of the product to display
     * @param model the {@link Model} object used to pass data to the view
     * @return the name of the view to render ("prod_d")
     */

    @GetMapping("/prod/{id}")
    public String showProductDetails(@PathVariable UUID id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        System.out.println(id);
        return "prod_d";
    }
}