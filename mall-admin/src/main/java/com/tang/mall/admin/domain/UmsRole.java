package com.tang.mall.admin.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UmsRole
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/21 23:07
 * @Created by ASUS
 */
/**
    * 后台用户角色表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ums_role")
public class UmsRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 后台用户数量
     */
    @Column(name = "admin_count")
    private Integer adminCount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 启用状态：0->禁用；1->启用
     */
    @Column(name = "`status`")
    private Integer status;

    @Column(name = "sort")
    private Integer sort;
}