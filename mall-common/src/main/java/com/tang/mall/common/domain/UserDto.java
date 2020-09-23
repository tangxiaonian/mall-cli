package com.tang.mall.common.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserDto {
    private String id;
    private String username;
    private String password;
    private Integer status;
    private String clientId;
    // 和 SecurityUser保持一致
    private List<String> authorities;
}
