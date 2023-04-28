package com.project.bongyang_club_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private String devUrl = "http://localhost:8080";

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);

        Info info = new Info()
                .title("Bong Yang CLUB API")
                .version("1.0");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }

}