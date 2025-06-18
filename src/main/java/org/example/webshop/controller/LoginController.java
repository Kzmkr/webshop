package org.example.webshop.controller;

import java.util.Optional;

import org.example.webshop.model.Address;
import org.example.webshop.model.User;
import org.example.webshop.repository.AddressRepository;
import org.example.webshop.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * Controller class for handling login and profile-related requests.
 * Provides endpoints for user login, profile viewing, and address management.
 */
@Controller
public class LoginController {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    /**
     * Constructs a new {@code LoginController} with the specified dependencies.
     *
     * @param userRepository the repository for user data
     * @param addressRepository the repository for address data
     */
    public LoginController(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

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

    /**
     * Handles GET requests to the "/profile" endpoint.
     * Retrieves the authenticated user's profile information and adds it to the model.
     * If the user is not authenticated or not found, redirects to the login page.
     *
     * @param model the model to which user data is added
     * @return the name of the profile view ("profile") or a redirect to the login page
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmail(username);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);
            } else {
                return "redirect:/login"; // Redirect if user is not found
            }
        } else {
            return "redirect:/login";
        }

        return "profile";
    }

    /**
     * Handles POST requests to the "/profile/add-address" endpoint.
     * Adds or updates the authenticated user's address with the provided details.
     * If the user is not authenticated, the request is ignored.
     *
     * @param street the street of the address
     * @param city the city of the address
     * @param zipCode the zip code of the address
     * @param state the state of the address
     * @param country the country of the address
     * @param houseNumber the house number of the address
     * @return a redirect to the profile page
     */
    @PostMapping("/profile/add-address")
    public String addOrUpdateAddress(@RequestParam String street,
                                     @RequestParam String city,
                                     @RequestParam String zipCode,
                                     @RequestParam String state,
                                     @RequestParam String country,
                                     @RequestParam String houseNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmail(username);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Address address = user.getAddress();

                if (address == null) {
                    address = new Address();
                }

                address.setStreet(street);
                address.setCity(city);
                address.setZipCode(zipCode);
                address.setState(state);
                address.setCountry(country);
                address.setHouseNumber(houseNumber);

                address = addressRepository.save(address);

                user.setAddress(address);
                userRepository.save(user);
            }
        }
        return "redirect:/profile";
    }
}