package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域问题和预检问题
 *
 * @author koveer
 * @since 3.0
 * - 2023/3/39 12:42
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:9528")
                .allowedMethods("OPTIONS","GET","POST","DELETE","PUT")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
