package dev.gooiman.server.config.web;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {

    return new OpenAPI()
        .components(new Components())
        .info(apiInfo());

  }

  private Info apiInfo() {
    return new Info()
        .title("구이만 DEV API")
        .description("구이만 DEV API 명세입니다.")
        .version("1.0.0");
  }
}
