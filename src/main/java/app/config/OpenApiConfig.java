package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
        .title("API para el sistema de CADET refactorizado")
        .version("1.0")
        .description("Descripcion detallada de los endpoints disponibles en la api"));
    }
}
