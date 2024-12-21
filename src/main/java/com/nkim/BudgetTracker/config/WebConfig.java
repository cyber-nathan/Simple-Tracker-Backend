package com.nkim.BudgetTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all paths
                        .allowedOrigins("http://127.0.0.1:4200", "http://localhost:4200", "https://hoppscotch.io/", "https://proxy.hoppscotch.io/") // Allow Angular frontend
                        .allowedMethods("*") // Include DELETE
                        .allowedHeaders("*"); // Allow all headers
//                        .allowCredentials(true); // If credentials (cookies, etc.) are needed
            }

//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:4200")
//                        .allowedMethods(HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.PATCH.name(), HttpMethod.OPTIONS.name())
//                        .allowedHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION)
//                        .allowCredentials(true);
//
//
//            }
        };
    }
}
