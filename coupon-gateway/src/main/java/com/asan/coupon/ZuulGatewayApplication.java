package com.asan.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Asan
 * @date 2021/6/7
 * 网关应用启动入口
 * @EnableZuulProxy 标识当前应用是一个zuul server
 * @SpringCloudApplication 组合了 springboot应用注解 + 服务发现注解 + 熔断注解
 */
@EnableZuulProxy
@EnableFeignClients     // 一定需要加入, 因为 permission-sdk 中写了 Feign 接口
@SpringCloudApplication
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }
}
