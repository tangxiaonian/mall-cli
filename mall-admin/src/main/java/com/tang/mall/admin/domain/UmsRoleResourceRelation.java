package com.tang.mall.admin.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UmsRoleResourceRelation
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/21 23:06
 * @Created by ASUS
 */
/**
    * 后台角色资源关系表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ums_role_resource_relation")
public class UmsRoleResourceRelation {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 资源ID
     */
    @Column(name = "resource_id")
    private Long resourceId;
}