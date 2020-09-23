package com.tang.mall.gateway.authorization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.nimbusds.jose.JWSObject;
import com.tang.mall.common.constant.AuthConstant;
import com.tang.mall.common.domain.UserDto;
import com.tang.mall.gateway.config.IgnoreUrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname AuthorizationManager
 * @Description [ 自定义认证接口管理,访问后端路径需要验证权限 ]
 * @Author Tang
 * @Date 2020/9/4 22:16
 * @Created by ASUS
 */
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    public IgnoreUrlsConfig ignoreUrlsConfig;

    @Resource
    public RedisTemplate<String, Object> redisTemplate;

    /**
     * 自定义鉴权方法
     * @param authentication 登录成功的用户信息
     * @param authorizationContext
     * @return
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
                                             AuthorizationContext authorizationContext) {
        ServerWebExchange serverWebExchange = authorizationContext.getExchange();
        ServerHttpRequest request = serverWebExchange.getRequest();
        log.info( "AuthorizationManager.....调用开始...." );
        String uriPath = request.getURI().getPath();
        PathMatcher pathMatcher = new AntPathMatcher();
        // 1.匹配白名单
        if (
                ignoreUrlsConfig.getUrls()
                        .stream()
                        .anyMatch(ignoreUrl -> pathMatcher.match(ignoreUrl, uriPath))
        ) {
            // 同意授权
            return Mono.just(new AuthorizationDecision(true));
        }
        // 2.跨越的预检请求
        if (request.getMethod() == HttpMethod.OPTIONS) {
            // 同意授权
            return Mono.just(new AuthorizationDecision(true));
        }
        // 3.夸端处理
        String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }
        String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        try {
            // 解析jwt生成的token
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            UserDto userDto = JSON.parseObject(userStr, UserDto.class);
            // 后台登录,匹配路径请求是后台
            if (AuthConstant.ADMIN_CLIENT_ID.equals(userDto.getClientId()) &&
                    !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uriPath)) {
                return Mono.just(new AuthorizationDecision(false));
            }
            // 前台登录，匹配路径是是后台
            if (AuthConstant.PORTAL_CLIENT_ID.equals(userDto.getClientId()) &&
                    pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uriPath)) {
                return Mono.just(new AuthorizationDecision(false));
            }
            // 非管理端路径直接放行
            if (!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uriPath)) {
                return Mono.just(new AuthorizationDecision(true));
            }
            log.info("当前请求得路径为：--->" + uriPath);
            // 管理端路径需要验证权限
            // 权限格式：role_map  /mall-admin/brand/**  3_订单管理员,2_订单管理员
            //                   /mall-admin/product/**  3_订单管理员,2_订单管理员
            Map<Object, Object> entries = redisTemplate.opsForHash()
                    .entries(AuthConstant.RESOURCE_ROLES_MAP_KEY);
            // 获取资源url对应的角色集合
            List<String> roleNames = entries.keySet().stream()
                    .filter(item -> pathMatcher.match(item.toString(), uriPath))
                    // 获取url对应的role_name 列表
                    .map(item_1 -> (JSONArray.parseArray(entries.get(item_1.toString()).toString(), String.class)))
                    .findFirst().orElse(new ArrayList<String>());
            if (roleNames.size() == 0) {
                return Mono.just(new AuthorizationDecision(false));
            }
            // 也可以通过 userDto.getAuthorities();  拿到角色列表
//            List<String> roles = userDto.getAuthorities();
//            System.out.println( "roles--->" );
//            roles.forEach(System.out::println);

            // 获取最新的 角色列表
            List<String> roles = roleNames
                    .stream()
                    .map(
                            // ROLE_5_超级管理员
                            roleName -> AuthConstant.AUTHORITY_PREFIX + roleName
                    ).collect(Collectors.toList());
            // 验证角色
            return authentication
                    .filter(Authentication::isAuthenticated)
                    .flatMapIterable(Authentication::getAuthorities)
                    .map(GrantedAuthority::getAuthority)
                    .any(roles::contains)
                    .map(AuthorizationDecision::new)
                    .defaultIfEmpty(new AuthorizationDecision(false));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Mono.just(new AuthorizationDecision(false));
    }

}