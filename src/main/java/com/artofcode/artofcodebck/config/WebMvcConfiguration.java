
package com.artofcode.artofcodebck.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Autoriser les requêtes provenant de cette origine
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Autoriser ces méthodes HTTP
                .allowedHeaders("*"); // Autoriser tous les en-têtes
    }
}
