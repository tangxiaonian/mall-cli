package com.tang.mall.auth.service;

import com.tang.mall.auth.domain.User;
import com.tang.mall.common.constant.AuthConstant;
import com.tang.mall.common.domain.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
        String clientId = request.getParameter("client_id");
        UserDto userDto;
        if (AuthConstant.ADMIN_CLIENT_ID.equals(clientId)) {
            userDto = umsAdminService.loadUserByUsername(account);
        }else {
            userDto = umsUserService.loadUserByUsername(account);
        }
        System.out.println("验证用户信息...." + account);
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("USER"));
        list.add(new SimpleGrantedAuthority("ADMIN"));
        return new User(1L,"admin-app",list, bCryptPasswordEncoder.encode("123456"),
                account, true, true, true, true);
    }
}