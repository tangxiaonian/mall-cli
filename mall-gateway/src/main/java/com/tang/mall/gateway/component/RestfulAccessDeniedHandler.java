package com.tang.mall.gateway.component;

import com.alibaba.fastjson.JSONObject;
import com.tang.mall.common.api.CommonResult;
import com.tang.mall.common.api.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @Classname RestfulAccessDeniedHandler
 * @Description [ 自定义无权限访问时 ]
 * @Author Tang
 * @Date 2020/9/4 22:19
 * @Created by ASUS
 */
@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        ServerHttpResponse serverHttpResponse = serverWebExchange.getResponse();
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        HttpHeaders headers = serverHttpResponse.getHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set("Access-Control-Allow-Origin","*");
        headers.set("Cache-Control","no-cache");
        // 返回结果
        String jsonResult = JSONObject.toJSONString(CommonResult.failed("不好意思，你没有权限访问!"));
        DataBufferFactory bufferFactory = serverHttpResponse.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(
                jsonResult.getBytes(Charset.defaultCharset())
        );
        return serverHttpResponse.writeWith(Mono.just(dataBuffer));
    }

}