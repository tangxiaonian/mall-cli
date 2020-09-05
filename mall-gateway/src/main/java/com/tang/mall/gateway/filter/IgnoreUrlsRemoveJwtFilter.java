package com.tang.mall.gateway.filter;

import com.tang.mall.common.constant.AuthConstant;
import com.tang.mall.gateway.config.IgnoreUrlsConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;


/**
 * @Classname IgnoreUrlsRemoveJwtFilter
 * @Description [ 白名单过滤器:只负责移除请求头 ]
 * @Author Tang
 * @Date 2020/9/4 22:34
 * @Created by ASUS
 */
@Component
public class IgnoreUrlsRemoveJwtFilter implements WebFilter {

    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        URI uri = request.getURI();
        // spring 路径匹配器
        PathMatcher pathMatcher = new AntPathMatcher();
        // uri 进行匹配
        if (ignoreUrlsConfig.getUrls().stream().anyMatch((ignoreUrl -> {
            return pathMatcher.match(ignoreUrl, uri.getPath());
        }))) {
            request = serverWebExchange.getRequest().mutate()
                    .header(AuthConstant.JWT_TOKEN_HEADER, "").build();
            serverWebExchange = serverWebExchange.mutate()
                    .request(request).build();
        }
        return webFilterChain.filter(serverWebExchange);
    }
}