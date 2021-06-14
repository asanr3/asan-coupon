package com.asan.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Asan
 * @date 2021/6/11
 * 模板微服务的启动入口
 * @EnableScheduling 定时任务注解
 * @EnableJpaAuditing 审计功能注解
 * @EnableEurekaClient 服务发现注解
 */
@EnableScheduling
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.asan.coupon.**.mapper")
public class TemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class,args);
    }
}
