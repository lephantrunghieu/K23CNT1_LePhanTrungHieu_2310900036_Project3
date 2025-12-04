package com.lpth.webLaptop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String UPLOAD_DIR = "file:src/main/resources/static/images/maytinh/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/maytinh/**")
                .addResourceLocations(UPLOAD_DIR);
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}