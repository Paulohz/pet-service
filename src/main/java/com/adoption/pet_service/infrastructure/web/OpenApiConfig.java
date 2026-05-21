package com.adoption.pet_service.infrastructure.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI petServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pet Service API")
                        .description("Microserviço responsável pelo gerenciamento de pets para adoção")
                        .version("1.0.0"));
    }
}
