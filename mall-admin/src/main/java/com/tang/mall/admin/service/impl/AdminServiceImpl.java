package com.tang.mall.admin.service.impl;
import java.util.Date;
import com.google.common.collect.Lists;

import com.tang.mall.admin.domain.UmsRole;
import com.tang.mall.admin.feign.AuthClientApi;
import com.tang.mall.admin.service.AdminService;
import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.constant.AuthConstant;
import com.tang.mall.common.domain.Oauth2TokenDto;
import com.tang.mall.common.domain.UserDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    public AuthClientApi authClientApi;

    @Override
    public boolean login(String username, String password) {
        Map<String, String> mapData = new HashMap<>();
        mapData.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        mapData.put("grant_type", "password");
        mapData.put("client_secret","secret");
        mapData.put("username", username);
        mapData.put("password", password);
        CommonResult<Oauth2TokenDto> result = authClientApi.postAccessToken(mapData);
        return result != null;
    }

    @Override
    public UserDto loadUserByUsername(String username) {
        UserDto userDto = new UserDto();
        userDto.setId("3");
        userDto.setUsername("管理员");
        userDto.setPassword("111");
        userDto.setStatus(0);
        userDto.setClientId(AuthConstant.ADMIN_CLIENT_ID);
        // 从用户资源关系表表里面查询数据

        List<UmsRole> roleList = new ArrayList<>();

        UmsRole umsRole_1 = new UmsRole();
        umsRole_1.setId(1L);
        umsRole_1.setName("商品管理员");
        umsRole_1.setDescription("");
        umsRole_1.setAdminCount(0);
        umsRole_1.setCreateTime(new Date());
        umsRole_1.setStatus(0);
        umsRole_1.setSort(0);

        UmsRole umsRole_2 = new UmsRole();
        umsRole_2.setId(2L);
        umsRole_2.setName("订单管理员");
        umsRole_2.setDescription("");
        umsRole_2.setAdminCount(0);
        umsRole_2.setCreateTime(new Date());
        umsRole_2.setStatus(0);
        umsRole_2.setSort(0);

        roleList.add(umsRole_1);
        roleList.add(umsRole_2);
        userDto.setRoles( roleList
                .stream()
                .map( item ->  item.getId() + "_" + item.getName())
                .collect(Collectors.toList())
        );
        return null;
    }
}
