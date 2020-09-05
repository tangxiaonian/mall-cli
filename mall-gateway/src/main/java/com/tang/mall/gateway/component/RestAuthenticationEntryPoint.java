package com.tang.mall.gateway.component;

import com.alibaba.fastjson.JSONObject;
import com.tang.mall.common.api.CommonResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @Classname RestAuthenticationEntryPoint
 * @Description [ 没有登录时访问自定义返回内容 ]
 * @Author Tang
 * @Date 2020/9/4 22:26
 * @Created by ASUS
 */
@Component
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        ServerHttpResponse serverHttpResponse = serverWebExchange.getResponse();
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        HttpHeaders headers = serverHttpResponse.getHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set("Access-Control-Allow-Origin","*");
        headers.set("Cache-Control","no-cache");
        // 返回结果
        String jsonResult = JSONObject.toJSONString(CommonResult.failed("可能没登陆，或者token过期了!"));
        DataBufferFactory bufferFactory = serverHttpResponse.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(
                jsonResult.getBytes(Charset.defaultCharset())
        );
        return serverHttpResponse.writeWith(Mono.just(dataBuffer));
    }
}