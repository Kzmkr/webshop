package org.example.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling login-related requests.
 * Maps the login page to the "/login" endpoint.
 */
@Controller
public class LoginController {

    /**
     * Handles GET requests to the "/login" endpoint.
     * Returns the name of the login view to be rendered.
     *
     * @return the name of the login view ("login")
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}