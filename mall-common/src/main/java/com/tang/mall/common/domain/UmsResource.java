package com.tang.mall.common.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 后台资源表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ums_resource")
public class UmsResource implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 资源名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 资源URL
     */
    @Column(name = "url")
    private String url;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 资源分类ID
     */
    @Column(name = "category_id")
    private Long categoryId;

    private static final long serialVersionUID = 1L;
}