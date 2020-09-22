package com.tang.mall.admin.service.impl;

import com.tang.mall.common.domain.UmsResource;
import com.tang.mall.common.domain.UmsRole;
import com.tang.mall.common.domain.UmsRoleResourceRelation;
import com.tang.mall.admin.mapper.UmsResourceMapper;
import com.tang.mall.admin.mapper.UmsRoleMapper;
import com.tang.mall.admin.mapper.UmsRoleResourceRelationMapper;
import com.tang.mall.admin.service.UmsResourceService;
import com.tang.mall.common.constant.AuthConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname UmsResourceServiceImpl
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/21 22:49
 * @Created by ASUS
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Resource
    UmsRoleMapper umsRoleMapper;

    @Resource
    UmsResourceMapper umsResourceMapper;

    @Resource
    UmsRoleResourceRelationMapper umsRoleResourceRelationMapper;

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void initResourceMap() {

        List<UmsRole> umsRoles = umsRoleMapper.selectList(null);
        List<UmsResource> umsResourceList = umsResourceMapper.selectList(null);
        List<UmsRoleResourceRelation> umsRoleResourceRelations = umsRoleResourceRelationMapper.selectList(null);

        Map<String,List<String>> resourceRoleMap = new HashMap<>();

        umsRoles.forEach((item) -> {
            // 获取资源id
            List<Long> resourceIds = umsRoleResourceRelations
                    .stream()
                    .filter(
                            umsRoleResourceRelation -> umsRoleResourceRelation.getRoleId().equals(item.getId())
                    ).map(
                            item_1 -> item_1.getResourceId()
                    )
                    .collect(Collectors.toList());
            // 拼接资源名称
            List<String> resourcesNames = umsResourceList.stream()
                    .filter(item_1 -> {
                        return resourceIds.contains(item_1.getId());
                    })
                    .map(item_2 -> "/" + applicationName + item_2.getUrl())
                    .collect(Collectors.toList());

            resourcesNames.forEach(resourcesName -> {
                List<String> list = Optional.ofNullable(
                        resourceRoleMap.get(resourcesName)
                ).orElse(new ArrayList<>());
                list.add(item.getId() + "_" + item.getName());
                resourceRoleMap.put(resourcesName,list);
            });

        });
        redisTemplate.opsForHash()
                .putAll(AuthConstant.RESOURCE_ROLES_MAP_KEY,resourceRoleMap);
    }
}