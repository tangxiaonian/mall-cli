package com.tang.mall.portal.service;

import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.domain.Oauth2TokenDto;
import com.tang.mall.common.domain.UserDto;

public interface UserService {

    public CommonResult<Oauth2TokenDto> login(String username, String password);

    UserDto loadUserByUsername(String username);
}
