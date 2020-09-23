package com.tang.mall.auth.service;

import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.domain.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname UmsAdminService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/20 21:06
 * @Created by ASUS
 */
@FeignClient(value = "mall-admin")
public interface UmsAdminService {

    @PostMapping("/admin/loadByUsername")
    CommonResult<UserDto> loadUserByUsername(@RequestParam String username);

}
