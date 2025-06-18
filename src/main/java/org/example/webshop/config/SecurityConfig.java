package org.example.webshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security settings.
 * Defines security rules, authentication mechanisms, and password encoding.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
          * - Disables CSRF protection (use cautiously, especially for APIs).
     * - Allows public access to specific endpoints such as "/", "/register", "/login", etc.
     * - Requires authentication for all other requests.
     * - Configures form-based login with a custom login page and a default success URL.
     * - Enables HTTP Basic authentication with a custom realm name.
     * - Configures logout to be accessible to all users.
     *
     * @param http the {@link HttpSecurity} object to configure security settings.
     * @return the configured {@link SecurityFilterChain}.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Typically disabled for APIs, be cautious
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(
                                "/register",
                                "/login",
                                "/static",
                                "/css/**",
                                "/uploads/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .httpBasic(basic -> basic.realmName("Webshop API"))
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    /**
     * Configures a password encoder bean using BCrypt hashing algorithm.
     *
     * @return a {@link PasswordEncoder} instance for encoding passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}