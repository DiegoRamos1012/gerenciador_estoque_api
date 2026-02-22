package com.diego_ramos.gerenciador_estoque.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Gerenciador de Estoque API")
                        .version("v1")
                        .description("Documentação da API para gerenciamento de estoque")
                        .contact(new Contact().name("Diego Ramos"))
                )
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Autenticação via token JWT. Faça login em /auth/login para obter o token.")));
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Autenticação")
                .pathsToMatch("/auth/**")
                .packagesToScan("com.diego_ramos.gerenciador_estoque.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("Gestão de Usuários")
                .pathsToMatch("/users/**")
                .packagesToScan("com.diego_ramos.gerenciador_estoque.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("Gestão de Produtos")
                .pathsToMatch("/products/**")
                .packagesToScan("com.diego_ramos.gerenciador_estoque.controller")
                .build();
    }
}
