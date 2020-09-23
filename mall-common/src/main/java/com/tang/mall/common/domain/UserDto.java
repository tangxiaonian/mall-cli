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
    private List<String> roles;
}
