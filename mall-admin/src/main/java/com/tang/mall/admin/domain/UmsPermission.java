package com.tang.mall.admin.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UmsPermission
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/21 23:07
 * @Created by ASUS
 */
/**
    * 后台用户权限表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ums_permission")
public class UmsPermission {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 父级权限id
     */
    @Column(name = "pid")
    private Long pid;

    /**
     * 名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 权限值
     */
    @Column(name = "`value`")
    private String value;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 前端资源路径
     */
    @Column(name = "uri")
    private String uri;

    /**
     * 启用状态；0->禁用；1->启用
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;
}