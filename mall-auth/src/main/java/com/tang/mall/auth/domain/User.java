package com.tang.mall.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

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
public class User implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;

    private String password;

    private String username;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

}