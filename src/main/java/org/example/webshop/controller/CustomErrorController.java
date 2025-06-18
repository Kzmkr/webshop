package org.example.webshop.controller;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Custom error controller to handle application errors.
 * This controller maps the "/error" endpoint to a custom error view.
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Handles requests to the "/error" endpoint.
     * This method returns the name of the error view to be rendered.
     *
     * @return the name of the error view ("error")
     */
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}