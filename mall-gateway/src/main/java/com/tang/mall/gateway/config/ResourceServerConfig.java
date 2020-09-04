package com.tang.mall.gateway.config;

import com.tang.mall.gateway.authorization.AuthorizationManager;
import com.tang.mall.gateway.component.RestAuthenticationEntryPoint;
import com.tang.mall.gateway.component.RestfulAccessDeniedHandler;
import com.tang.mall.gateway.filter.IgnoreUrlsRemoveJwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

/**
 * @Classname ResourceServerConfig
 * @Description [ 资源配置类 ]
 * @Author Tang
 * @Date 2020/9/4 20:04
 * @Created by ASUS
 */
@EnableWebFluxSecurity
@Configuration
public class ResourceServerConfig {

    private AuthorizationManager authorizationManager;
    private IgnoreUrlsConfig ignoreUrlsConfig;
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

}