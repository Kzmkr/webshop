package org.example.webshop.controller;

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


import java.util.List;

@Controller
public class UploadController {


    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public UploadController(ProductService productService, CategoryService categoryService) {

        this.productService = productService;
        this.categoryService = categoryService;
    }


    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "upload";
    }

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

