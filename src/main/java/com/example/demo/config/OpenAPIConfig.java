package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

  @Bean
  public OpenAPI customOpenAPI(
    @Value("${application-description}") String appDescription,
    @Value("${application-version}") String appVersion
  ) {
    return new OpenAPI()
      .components(
        new Components()
          .addSecuritySchemes(
            "bearer-key",
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
          )
      )
      .info(
        new Info()
          .title("Scratch API")
          .version(appVersion)
          .description(appDescription)
          .termsOfService("https://swagger.io/terms/")
          .license(new License().name("Apache 2.0").url("https://springdoc.org"))
      );
  }
}
