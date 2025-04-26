package com.pizzaparty.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS configuration for the application.
 * This class enables CORS rules to allow HTTP requests from specific domains.
 * Currently, it allows access from localhost:3000 with selected methods.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configure CORS rules for the specified routes

        registry.addMapping("/**") // Apply the CORS configuration to all routes (every endpoint of the app)
                .allowedOrigins("http://localhost:3000") // Allow requests only from the domain localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow the use of HTTP methods GET, POST, PUT, DELETE, and OPTIONS
                .allowedHeaders("*") // Allow any header in the request (e.g., Content-Type, Authorization)
                .allowCredentials(true); // Allow sending credentials like cookies and authorization headers
    }
}
