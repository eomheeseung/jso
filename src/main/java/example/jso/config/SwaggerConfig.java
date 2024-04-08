package example.jso.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPI30
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(info());
    }
    private Info info() {
        return new Info().title("jwt-security-oauth2")
                .description("jwt와 security와 oauth2를 적용한 application")
                .version("v1");
    }
}
