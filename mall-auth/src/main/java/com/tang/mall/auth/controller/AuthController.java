package com.tang.mall.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname AuthController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/8/29 11:28
 * @Created by ASUS
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/index")
    public String index() {
        return "AuthController index...";
    }

}