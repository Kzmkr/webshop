package org.example.webshop.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.webshop.model.User;
import org.example.webshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class RegistrationControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        // Arrange
        User user = new User();
        user.setPassword("plainPassword");
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode("plainPassword")).thenReturn(encodedPassword);

        // Act
        String redirectUrl = registrationController.registerUser(user);

        // Assert
        assertNotNull(redirectUrl);
        assertEquals("redirect:/login", redirectUrl);
        assertEquals(encodedPassword, user.getPassword());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(user);
    }
}