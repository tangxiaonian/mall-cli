package com.tang.mall.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname AuthorizationServerConfiguration
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/8/30 18:39
 * @Created by ASUS
 */
@Import(JwtAccessTokenConverter.class)
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    public AuthenticationManager authenticationManager;

    @Resource
    public JwtTokenEnhancer jwtTokenEnhancer;

    @Resource
    public JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * redis 方式生成token
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("admin-app")
                .secret(bCryptPasswordEncoder.encode("secret"))
                .scopes("scope")
                .authorizedGrantTypes("password", "refresh_token")
                .resourceIds("backend")
                .accessTokenValiditySeconds(60 * 60 * 24)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 30)
                .and()
                .withClient("portal-app")
                .secret(bCryptPasswordEncoder.encode("secret"))
                .scopes("scope")
                .authorizedGrantTypes("password", "refresh_token")
                .resourceIds("backend")
                .accessTokenValiditySeconds(60 * 60 * 24)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 30);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 增强器集合
        List<TokenEnhancer> tokenEnhancerList = new ArrayList<>();
        tokenEnhancerList.add(jwtTokenEnhancer);
        tokenEnhancerList.add(accessTokenConverter());

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(tokenEnhancerList);

        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }
}