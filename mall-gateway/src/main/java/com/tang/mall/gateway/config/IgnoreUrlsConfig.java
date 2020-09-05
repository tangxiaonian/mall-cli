package com.tang.mall.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Classname IgnoreUrlsConfig
 * @Description [ 网关白名单 ]
 * @Author Tang
 * @Date 2020/9/4 22:17
 * @Created by ASUS
 */
@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "secure.ignore.urls")
public class IgnoreUrlsConfig {
    private List<String> urls;
}