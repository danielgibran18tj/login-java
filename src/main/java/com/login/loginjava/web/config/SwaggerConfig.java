package com.microservicios.clinica_veter_branch_service.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Clinica Veterinaria")
                        .version("1.0"));
    }
}
    //http://localhost:8080/veterinaria/api/swagger-ui/index.html
