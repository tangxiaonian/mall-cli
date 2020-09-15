package com.tang.mall.portal.controller;

import com.tang.mall.common.api.CommonResult;
import com.tang.mall.portal.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    public UserService userService;

    public CommonResult<String> login(String username, String password) {

        return CommonResult.success(null);
    }

}