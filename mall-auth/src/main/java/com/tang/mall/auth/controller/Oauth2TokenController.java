package com.tang.mall.auth.controller;

import com.tang.mall.auth.domain.Oauth2TokenDto;
import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.constant.AuthConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.Map;

/**
 * @Classname Oauth2TokenController
 * @Description [ 自定义Oauth2获取令牌接口 ]
 * @Author Tang
 * @Date 2020/8/31 22:27
 * @Created by ASUS
 */
@RequestMapping("/oauth")
@RestController
public class Oauth2TokenController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @RequestMapping(value = "/token", method= RequestMethod.POST)
    public CommonResult<Oauth2TokenDto> postAccessToken(@ApiIgnore Principal principal,
                                                        @ApiIgnore @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();

        Oauth2TokenDto oauth2TokenDto = new Oauth2TokenDto(oAuth2AccessToken.getValue(),
                oAuth2AccessToken.getRefreshToken().getValue(),
                AuthConstant.JWT_TOKEN_PREFIX,
                oAuth2AccessToken.getExpiresIn());

        return CommonResult.success(oauth2TokenDto);
    }

}