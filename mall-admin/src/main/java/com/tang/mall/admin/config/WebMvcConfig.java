package com.tang.mall.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Classname WebMvcConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/8/30 12:26
 * @Created by ASUS
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true).maxAge(3600);
    }

}