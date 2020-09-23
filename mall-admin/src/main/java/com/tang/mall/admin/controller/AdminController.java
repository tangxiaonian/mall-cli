package com.tang.mall.admin.controller;

import com.tang.mall.admin.service.AdminService;
import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.domain.Oauth2TokenDto;
import com.tang.mall.common.domain.UserDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname AuthController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/8/29 11:28
 * @Created by ASUS
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    public AdminService adminService;

    @GetMapping("/index")
    public String index() {
        return "admin get resource success....";
    }

    @PostMapping("/loadByUsername")
    public CommonResult<UserDto> loadUserByUsername(@RequestParam String username) {
        return CommonResult.success(adminService.loadUserByUsername(username));
    }

    @PostMapping("/login")
    public CommonResult<Oauth2TokenDto> login(String username, String password){
        return adminService.login(username, password);
    }
}