package com.asan.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <h1>权限服务启动程序</h1>
 * @author Asan
 */
@EnableEurekaClient
@SpringBootApplication
public class PermissionApplication {

    public static void main(String[] args) {

        SpringApplication.run(PermissionApplication.class, args);
    }
}
