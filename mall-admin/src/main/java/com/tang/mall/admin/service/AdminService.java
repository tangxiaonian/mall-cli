package com.tang.mall.admin.service;

import com.tang.mall.common.domain.UserDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface AdminService {

    public boolean login(String username, String password);

    public UserDto loadUserByUsername(@RequestParam String username);

}
