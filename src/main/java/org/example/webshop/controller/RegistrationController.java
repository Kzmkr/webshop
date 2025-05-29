package org.example.webshop.controller;

import org.example.webshop.model.User;
import org.example.webshop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for handling user registration.
 * Maps requests related to user registration to the appropriate endpoints.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new {@code RegistrationController} with the specified dependencies.
     *
     * @param userRepository the repository used to manage user data
     * @param passwordEncoder the encoder used to hash user passwords
     */
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Handles GET requests to display the registration form.
     *
     * @return the name of the registration view ("registration")
     */
    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    /**
     * Handles POST requests to register a new user.
     * Encodes the user's password and saves the user to the repository.
     * Redirects to the login page upon successful registration.
     *
     * @param user the {@link User} object containing the user's registration details
     * @return a redirect to the login page ("/login")
     */
    @PostMapping
    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}