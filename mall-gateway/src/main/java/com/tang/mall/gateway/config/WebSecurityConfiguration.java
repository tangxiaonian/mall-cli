package com.tang.mall.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Classname WebSecurityConfiguration
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/8/30 19:08
 * @Created by ASUS
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true,securedEnabled = true)
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .mvcMatchers("/v2/api-docs").permitAll()
                .anyRequest().authenticated();
    }

}