package com.tang.mall.admin.service.impl;

import com.tang.mall.admin.domain.UmsPermission;
import com.tang.mall.admin.domain.UmsRole;
import com.tang.mall.admin.domain.UmsRoleResourceRelation;
import com.tang.mall.admin.mapper.UmsPermissionMapper;
import com.tang.mall.admin.mapper.UmsRoleMapper;
import com.tang.mall.admin.mapper.UmsRoleResourceRelationMapper;
import com.tang.mall.admin.service.UmsResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

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
    UmsPermissionMapper umsPermissionMapper;

    @Resource
    UmsRoleResourceRelationMapper umsRoleResourceRelationMapper;


    @Override
    public void initResourceMap() {

        List<UmsRole> umsRoles = umsRoleMapper.selectList(null);
        List<UmsPermission> umsPermissionList = umsPermissionMapper.selectList(null);
        List<UmsRoleResourceRelation> umsRoleResourceRelations = umsRoleResourceRelationMapper.selectList(null);
        // todo

    }
}