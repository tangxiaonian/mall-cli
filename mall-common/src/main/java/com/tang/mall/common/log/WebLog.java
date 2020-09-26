package com.tang.mall.common.log;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname WebLog
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/9/24 23:08
 * @Created by ASUS
 */
@ToString
@Data
public class WebLog {
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 执行结果
     */
    private Object result;

    /**
     * 入参列表
     */
    private Map<String, Object> parameters;

    /**
     * 异常信息
     */
    private String throwableMessage;

    /**
     * 状态 NORMAL,ERROR
     */
    private String status = "NORMAL";

    private Date startTime;

    private Date endTime;

    /**
     * 执行时间
     */
    private Long speed;
}