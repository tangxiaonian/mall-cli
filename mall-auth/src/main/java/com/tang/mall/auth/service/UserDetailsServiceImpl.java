package com.tang.mall.auth.service;

import com.tang.mall.auth.domain.SecurityUser;
import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.constant.AuthConstant;
import com.tang.mall.common.domain.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Classname UserDeatilServiceImpl
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/8/30 21:44
 * @Created by ASUS
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    public HttpServletRequest request;

    @Resource
    UmsAdminService umsAdminService;

    @Resource
    UmsUserService umsUserService;

    @Override
    public UserDetails loadUserByUsername(String account)
            throws UsernameNotFoundException {
        System.out.println("验证用户信息...." + account);
        String clientId = request.getParameter("client_id");
        System.out.println("clientId..." + clientId);
        CommonResult<UserDto> userDto;
        // 从数据库根据账号获取用户信息
        if (AuthConstant.ADMIN_CLIENT_ID.equals(clientId)) {
            userDto = umsAdminService.loadUserByUsername(account);
        }else {
            userDto = umsUserService.loadUserByUsername(account);
        }
        userDto.getData().setPassword(
                bCryptPasswordEncoder.encode(userDto.getData().getPassword())
        );
        return new SecurityUser(userDto.getData());
    }
}