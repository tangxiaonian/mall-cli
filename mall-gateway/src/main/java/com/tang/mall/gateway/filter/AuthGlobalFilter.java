package com.tang.mall.gateway.filter;

import com.nimbusds.jose.JWSObject;
import com.tang.mall.common.constant.AuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * @Classname AuthGlobalFilter
 * @Description [ 全局过滤器:将用户信息放到全局 ]
 * @Author Tang
 * @Date 2020/9/23 17:48
 * @Created by ASUS
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String token = exchange.getRequest()
                .getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        if (token != null) {
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            try {
                String userStr = JWSObject.parse(realToken).getPayload().toString();
                log.info("AuthGlobalFilter.Filter() user:{}",userStr);
                ServerHttpRequest serverHttpRequest = exchange
                        .getRequest()
                        .mutate()
                        .header(AuthConstant.USER_TOKEN_HEADER, userStr)
                        .build();
                // 重新构建 ServerWebExchange
                exchange = exchange.mutate()
                        .request(serverHttpRequest).build();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}