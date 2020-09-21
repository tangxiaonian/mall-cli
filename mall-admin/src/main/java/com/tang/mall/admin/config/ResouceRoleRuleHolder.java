package com.tang.mall.admin.config;

import com.tang.mall.admin.service.UmsResourceService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Classname ResouceRoleRuleHolder
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/21 23:12
 * @Created by ASUS
 */
@Component
public class ResouceRoleRuleHolder {

    @Resource
    UmsResourceService umsResourceService;

    /**
     * 单一职责原则
     */
    @PostConstruct
    private void initRoleResource() {
        umsResourceService.initResourceMap();
    }

}