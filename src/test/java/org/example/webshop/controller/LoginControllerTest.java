package org.example.webshop.controller;

import org.springframework.ui.Model;
import org.example.webshop.model.Address;
import org.example.webshop.model.User;
import org.example.webshop.repository.AddressRepository;
import org.example.webshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testAddOrUpdateAddress_UserExists() {
        // Mock authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("user@example.com");
        when(authentication.getName()).thenReturn("user@example.com");

        // Mock user and address
        User user = new User();
        Address address = new Address();
        user.setAddress(address);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method
        String result = loginController.addOrUpdateAddress("123 Main St", "City", "12345", "State", "Country", "10");

        // Verify interactions
        verify(userRepository).findByEmail("user@example.com");
        verify(addressRepository).save(any(Address.class));
        verify(userRepository).save(user);

        // Assert result
        assertEquals("redirect:/profile", result);
        assertEquals("123 Main St", user.getAddress().getStreet());
        assertEquals("City", user.getAddress().getCity());
        assertEquals("12345", user.getAddress().getZipCode());
        assertEquals("State", user.getAddress().getState());
        assertEquals("Country", user.getAddress().getCountry());
        assertEquals("10", user.getAddress().getHouseNumber());
    }

    @Test
    void testAddOrUpdateAddress_UserNotFound() {
        // Mock authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("user@example.com");
        when(authentication.getName()).thenReturn("user@example.com");

        // Mock user not found
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        // Call the method
        String result = loginController.addOrUpdateAddress("123 Main St", "City", "12345", "State", "Country", "10");

        // Verify interactions
        verify(userRepository).findByEmail("user@example.com");
        verifyNoInteractions(addressRepository);

        // Assert result
        assertEquals("redirect:/profile", result);
    }

    @Test
    void testAddOrUpdateAddress_AnonymousUser() {
        // Mock anonymous user
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        // Call the method
        String result = loginController.addOrUpdateAddress("123 Main St", "City", "12345", "State", "Country", "10");

        // Verify no interactions with repositories
        verifyNoInteractions(userRepository);
        verifyNoInteractions(addressRepository);

        // Assert result
        assertEquals("redirect:/profile", result);
    }
    @Test
    void testProfile_AnonymousUser() {
        // Mock anonymous user
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        // Call the method
        String viewName = loginController.profile(model);

        // Verify no interactions with the repository or model
        verifyNoInteractions(userRepository);
        verifyNoInteractions(model);

        // Assert view name
        assertEquals("redirect:/login", viewName, "View name should be 'redirect:/login'");
    }
}