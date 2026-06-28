package togama.demo_backend_olimpiadas.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Olimpiadas Escolares - CTI")
                        .version("1.0")
                        .description("Documentación oficial del backend para el módulo de cursos e inscripciones."));
    }
}