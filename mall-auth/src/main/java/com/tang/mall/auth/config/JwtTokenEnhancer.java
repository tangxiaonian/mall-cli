package com.tang.mall.auth.config;

import com.tang.mall.auth.domain.SecurityUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname JwtTokenEnhancer
 * @Description [ jwt token 内容增强器 ]
 * @Author Tang
 * @Date 2020/8/31 21:45
 * @Created by ASUS
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
        //把用户ID设置到JWT中
        info.put("id", principal.getId());
        // 客户端平台
        info.put("client_id",principal.getClientId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}