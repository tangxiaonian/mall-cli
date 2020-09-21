package com.tang.mall.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(value = {"com.tang.mall.portal.mapper"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MallPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallPortalApplication.class, args);
    }

}
