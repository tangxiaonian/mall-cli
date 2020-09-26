package com.tang.mall.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(value = {"com.tang.mall.admin.mapper"})
@SpringBootApplication(scanBasePackages = {"com.tang.mall"})
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients
public class MallAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallAdminApplication.class, args);
    }

}
