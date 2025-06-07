package org.example.webshop.controller;



import static org.junit.jupiter.api.Assertions.*;

        import org.junit.jupiter.api.Test;

class LoginControllerTest {

    private final LoginController loginController = new LoginController();

    @Test
    void testLoginEndpoint() {
        // Act
        String viewName = loginController.login();

        // Assert
        assertNotNull(viewName);
        assertEquals("login", viewName);
    }
}