package com.asan.coupon.controller;

import com.asan.coupon.exception.CouponException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>健康检查接口（只检查自身项目，不对外）</>
 * @author Asan
 * @date 2021/6/14
 * 通过网关访问接口：127.0.0.1:9000/asan  9000是网关的端口号，asan是网关前缀
 */
@Slf4j
@RestController
public class HealthCheck {

    /** 服务发现客户端 */
    private final DiscoveryClient client;

    /** 服务注册接口，提供了获取服务 id 的方法 */
    private final Registration registration;

    public HealthCheck(DiscoveryClient client, Registration registration) {
        this.client = client;
        this.registration = registration;
    }

    /**
     * <h2>健康检查接口</>
     *  127.0.0.1:7001/coupon-template/health
     *  127.0.0.1:9000/asan/coupon-template/health
     * */
    @GetMapping("/health")
    public String health(){
        log.debug("view health api");
        return "CouponTemplate Is OK!";
    }

    /**
     * <h2>异常测试接口</>
     * 127.0.0.1:7001/coupon-template/exception
     * 127.0.0.1:9000/asan/coupon-template/exception
     * */
    @GetMapping("/exception")
    public String exception() throws CouponException {
        log.debug("view exception api");
        throw new CouponException("CouponTemplate Has Some Problem");
    }

    /**
     * <h2>获取Eureka Server 上的微服务元信息</>
         * 127.0.0.1:7001/coupon-template/info
     * 127.0.0.1:9000/asan/coupon-template/info
     * */
    @GetMapping("/info")
    public List<Map<String, Object>> info(){

        // 大约需要等待两分钟时间才能获取到注册信息
        List<ServiceInstance> instances =
                client.getInstances(registration.getServiceId());

        List<Map<String, Object>> result =
                new ArrayList<>(instances.size());

        instances.forEach(i -> {
            Map<String, Object> info = new HashMap<>();
            info.put("serviceId", i.getServiceId());
            info.put("instanceId", i.getInstanceId());
            info.put("port", i.getPort());

            result.add(info);
        });

        return result;
    }
}
