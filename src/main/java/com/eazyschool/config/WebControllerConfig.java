package com.eazyschool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebControllerConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("", "/public/home");
        registry.addRedirectViewController("/", "/public/home");
        registry.addViewController("public/courses").setViewName("fragments/courses");
        registry.addViewController("public/about").setViewName("fragments/about");
    }
}
