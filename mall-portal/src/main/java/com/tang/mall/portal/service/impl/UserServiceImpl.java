package com.tang.mall.portal.service.impl;

import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.constant.AuthConstant;
import com.tang.mall.common.domain.Oauth2TokenDto;
import com.tang.mall.portal.feign.AuthClientApi;
import com.tang.mall.portal.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    public AuthClientApi authClientApi;

    @Override
    public boolean login(String username, String password) {
        Map<String, String> mapData = new HashMap<>();
        mapData.put("client_id", AuthConstant.PORTAL_CLIENT_ID);
        mapData.put("grant_type", "password");
        mapData.put("client_secret","secret");
        mapData.put("username", username);
        mapData.put("password", password);
        CommonResult<Oauth2TokenDto> result = authClientApi.postAccessToken(mapData);
        return result != null;
    }
}
