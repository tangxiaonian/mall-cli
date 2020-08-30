package com.tang.mall.gateway.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname SwaggerResourcesProvider
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/8/30 10:24
 * @Created by ASUS
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    // 注入路由规则
    private final RouteLocator routeLocator;
    // 注入网关配置文件
    private final GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        // 动态获取所有服务Id,存放在 routes 集合
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        // 从配置文件中获取存在的路由信息,封装接口路径
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId())).forEach(route -> {
            route.getPredicates().stream()
                    .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                    .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId() + "-api",
                            predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                    .replace("**", "v2/api-docs?group="+route.getId()+"-api"))));
        });
//        api文档聚合接口形式：
//        resources.add(swaggerResource("mall-auth-api", "http://localhost:9082/v2/api-docs?group=mall-auth-api"));

//        gatewayProperties.getRoutes()
//                .stream()
//                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
//                .filter(routeDefinition -> {
//                    return routes.contains(routeDefinition.getId());
//                }).forEach(route -> {
//            route.getPredicates().stream()
//                    .filter(predicateDefinition -> {
//                        System.out.println("predicate name-->" + predicateDefinition.getName());
//                        return ("Path").equalsIgnoreCase(predicateDefinition.getName());
//                    }).forEach(predicateDefinition -> {
//                predicateDefinition.getArgs().forEach((key, value) -> {
//                    System.out.println("key--->" + key + ",value--->" + value);
//                });
//            });
//        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{},location:{}",name,location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}