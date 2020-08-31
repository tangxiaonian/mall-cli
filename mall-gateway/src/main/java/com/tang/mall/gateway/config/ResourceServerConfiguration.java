package com.tang.mall.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @Classname ResourceServerConfiguration
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/8/30 18:40
 * @Created by ASUS
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration implements ResourceServerConfigurer {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

    }
}