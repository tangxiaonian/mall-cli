package com.tang.mall.auth.service;

import com.tang.mall.common.domain.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname UmsAdminService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/20 21:06
 * @Created by ASUS
 */
@FeignClient(value = "mall-portal")
public interface UmsUserService {

    @GetMapping("/user/loadByUsername")
    UserDto loadUserByUsername(@RequestParam String username);

}
