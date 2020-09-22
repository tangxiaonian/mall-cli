package com.tang.mall.portal.controller;

import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.domain.Oauth2TokenDto;
import com.tang.mall.common.domain.UserDto;
import com.tang.mall.portal.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @PostMapping("/login")
    public CommonResult<Oauth2TokenDto> login(String username, String password){
        return userService.login(username, password);
    }

    @PostMapping("/loadByUsername")
    public CommonResult<UserDto> loadUserByUsername(@RequestParam String username) {
        return CommonResult.success(userService.loadUserByUsername(username));
    }

}