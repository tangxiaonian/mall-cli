package com.tang.mall.gateway;

import com.alibaba.fastjson.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MallGatewayApplicationTests {

    @Resource
    public RedisTemplate<String, Object> redisTemplate;

    @Test

    void contextLoads() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("11");
        strings.add("22");
        redisTemplate
                .opsForHash()
                .put("redis", "1", strings);

        String value = redisTemplate.opsForHash()
                .get("redis", "1").toString();

        List<String> list = JSONArray.parseArray(value, String.class);

        System.out.println(list.size());

    }

}
