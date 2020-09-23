package com.tang.mall.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ProductAttribute
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/23 14:08
 * @Created by ASUS
 */
@RestController
@RequestMapping("/productAttribute")
public class ProductAttributeController {

    @GetMapping("/all")
    public String getAll() {
        return "getAll....1,5";
    }
}