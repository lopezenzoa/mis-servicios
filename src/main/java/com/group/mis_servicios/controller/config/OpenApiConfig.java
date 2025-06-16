package com.group.mis_servicios.controller.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🚀 API Servicios") // Título principal
                        .version("1.0.0")                    // Versión
                        .description("Documentación oficial de la API REST")); // Descripción
    }
}

