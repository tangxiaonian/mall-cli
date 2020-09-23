package com.tang.mall.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Classname RootConfiguration
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/23 11:52
 * @Created by ASUS
 */
@Configuration
public class RootConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}