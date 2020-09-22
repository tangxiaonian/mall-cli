package com.tang.mall.auth.domain;

import com.tang.mall.common.domain.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @Classname User
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/8/30 21:46
 * @Created by ASUS
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登录客户端ID
     */
    private String clientId;

    private Collection<? extends GrantedAuthority> authorities;

    private String password;

    private String username;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    public SecurityUser(UserDto userDto) {
        this.clientId = userDto.getClientId();
        this.id = Long.parseLong(userDto.getId());
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.setAuthorities(userDto
                .getRoles()
                .stream()
                .map(item -> new SimpleGrantedAuthority(item))
                .collect(Collectors.toList())
        );
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }
}