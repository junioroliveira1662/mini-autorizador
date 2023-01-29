package br.com.vr.autorizador.api.core.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${info.app.version}")
    private String appVersion;
    @Value("${info.app.name}")
    private String appName;
    @Value("${info.app.description}")
    private String appDescription;

    @Bean
    public OpenAPI configOpenAPIDefinition() {
        return new OpenAPI().info(new Info().title(appName).description(appDescription).version(appVersion));
    }

    @Bean
    public GroupedOpenApi configApiV1OpenApi() {
        return GroupedOpenApi.builder().group("v1.0").packagesToScan("br.com.vr.autorizador.api.controller").pathsToMatch("/**").build();
    }

}
