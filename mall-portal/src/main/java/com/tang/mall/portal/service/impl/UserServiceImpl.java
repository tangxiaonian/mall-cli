package com.tang.mall.portal.service.impl;

import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.constant.AuthConstant;
import com.tang.mall.common.domain.Oauth2TokenDto;
import com.tang.mall.common.domain.UmsRole;
import com.tang.mall.common.domain.UserDto;
import com.tang.mall.portal.feign.AuthClientApi;
import com.tang.mall.portal.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    public AuthClientApi authClientApi;

    @Override
    public CommonResult<Oauth2TokenDto> login(String username, String password) {
        Map<String, String> mapData = new HashMap<>();
        mapData.put("client_id", AuthConstant.PORTAL_CLIENT_ID);
        mapData.put("grant_type", "password");
        mapData.put("client_secret","secret");
        mapData.put("scope","scope");
        mapData.put("username", username);
        mapData.put("password", password);
        CommonResult<Oauth2TokenDto> result = authClientApi.postAccessToken(mapData);
        return result;
    }

    @Override
    public UserDto loadUserByUsername(String username) {
        System.out.println("调用了User的loadUserByUsername...");
        UserDto userDto = new UserDto();
        userDto.setId("4");
        userDto.setUsername("用户4");
        userDto.setPassword("123");
        userDto.setStatus(0);
        userDto.setClientId(AuthConstant.PORTAL_CLIENT_ID);
        // 从用户资源关系表表里面查询数据

        List<UmsRole> roleList = new ArrayList<>();

        UmsRole umsRole_1 = new UmsRole();
        umsRole_1.setId(2L);
        umsRole_1.setName("订单管理员");
        umsRole_1.setDescription("");
        umsRole_1.setAdminCount(0);
        umsRole_1.setCreateTime(new Date());
        umsRole_1.setStatus(0);
        umsRole_1.setSort(0);

//        UmsRole umsRole_2 = new UmsRole();
//        umsRole_2.setId(2L);
//        umsRole_2.setName("订单管理员");
//        umsRole_2.setDescription("");
//        umsRole_2.setAdminCount(0);
//        umsRole_2.setCreateTime(new Date());
//        umsRole_2.setStatus(0);
//        umsRole_2.setSort(0);

        roleList.add(umsRole_1);
//        roleList.add(umsRole_2);
        // 权限列表
        userDto.setAuthorities( roleList
                .stream()
                .map( item ->  item.getId() + "_" + item.getName())
                .collect(Collectors.toList())
        );
        return userDto;
    }
}
