package com.project.bongyang_club_backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class

WebConfig implements WebMvcConfigurer {

    private final Path FILE_POSTER_ROOT = Paths.get("./poster").toAbsolutePath().normalize();

    private final Path FILE_JOURNAL_ROOT = Paths.get("./journal").toAbsolutePath().normalize();

    private final Path FILE_IMAGE_ROOT = Paths.get("./image").toAbsolutePath().normalize();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/poster/**")
                .addResourceLocations(FILE_POSTER_ROOT.toUri().toString());

        registry
                .addResourceHandler("/journal/**")
                .addResourceLocations(FILE_JOURNAL_ROOT.toUri().toString());
        registry
                .addResourceHandler("/image/**")
                .addResourceLocations(FILE_IMAGE_ROOT.toUri().toString());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3000);
    }

}
