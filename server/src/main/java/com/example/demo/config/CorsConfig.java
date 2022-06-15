package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
      .addMapping("/**")
      .allowCredentials(true)
      .exposedHeaders("X-Total-Count")
      .allowedMethods("PUT", "DELETE", "GET", "POST")
      .allowedOrigins("http://localhost:3000");
  }
}
