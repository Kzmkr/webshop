package org.example.webshop.controller;

import org.example.webshop.model.Product;
import org.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;


    @Autowired
    public HomeController(ProductService productService) {

        this.productService = productService;
    }




    @GetMapping("/")
    public String login(Model model) {
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        return "home";
    }
}
