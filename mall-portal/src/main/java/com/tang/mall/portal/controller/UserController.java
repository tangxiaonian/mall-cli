package com.tang.mall.portal.controller;

import com.tang.mall.common.api.CommonResult;
import com.tang.mall.portal.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @PostMapping("/login")
    public CommonResult<String> login(String username, String password) {
        boolean result = userService.login(username, password);
        return result ? CommonResult.success(null) : CommonResult.failed();
    }

}