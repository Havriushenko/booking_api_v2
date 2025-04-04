package com.test_task.booking_api_v2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI(){
    return new OpenAPI().info(new Info().title("Booking REST API").version("0.0.1"));
  }
}
