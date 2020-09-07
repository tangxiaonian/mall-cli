package com.tang.mall.gateway.config;

import com.tang.mall.common.constant.AuthConstant;
import com.tang.mall.gateway.authorization.AuthorizationManager;
import com.tang.mall.gateway.component.RestAuthenticationEntryPoint;
import com.tang.mall.gateway.component.RestfulAccessDeniedHandler;
import com.tang.mall.gateway.filter.IgnoreUrlsRemoveJwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @Classname ResourceServerConfig
 * @Description [ 资源配置类 ]
 * @Author Tang
 * @Date 2020/9/4 20:04
 * @Created by ASUS
 */
@EnableWebFluxSecurity
@Configuration
public class ResourceServerSecurityConfig {

    @Resource
    private AuthorizationManager authorizationManager;

    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    /**
     * webflux版的securityWebFilterChain
     * WebSecurityConfiguration
     * WebFluxSecurityConfiguration
     * @param http
     * @return
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // 自定义错误处理，token过期，没登录错误提示。
        http.oauth2ResourceServer()
                .authenticationEntryPoint(restAuthenticationEntryPoint);

        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

        // 在权限验证之前，添加一个白名单过滤器
        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.FORM_LOGIN);

        // 权限路径匹配
        http.authorizeExchange()
                .pathMatchers(ignoreUrlsConfig.getUrls().toArray(new String[]{})) // 忽略拦截的路径
                .permitAll()
                .and()
                .authorizeExchange()
                .anyExchange()
                .access(authorizationManager)
                .and()
                .csrf()
                .disable();

        // 异常处理
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);

        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 权限前缀  todo
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        // 权限名字
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }


}