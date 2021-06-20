package com.asan.coupon.feign.hystrix;

import com.asan.coupon.feign.TemplateClient;
import com.asan.coupon.vo.CommonResponse;
import com.asan.coupon.vo.CouponTemplateSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <h1>优惠券模板 Feign 接口的熔断降级策略</h1>
 * Hystrix 中需要实现Feign Cline中定义的所有方法（方法签名一定要保证完全一致），所有最简单的办法是实现Feign接口
 * @author Asan
 */
@Slf4j
@Component
public class TemplateClientHystrix implements TemplateClient {

    /**
     * <h2>查找所有可用的优惠券模板</h2>
     * 自定义熔断降级策略，这里只做两件事：
     * 1、打印错误日志，记录调用异常
     * 2、返回兜底数据，空列表
     */
    @Override
    public CommonResponse<List<CouponTemplateSDK>> findAllUsableTemplate() {

        log.error("[eureka-client-coupon-template] findAllUsableTemplate " +
                "request error");
        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-template] request error",
                Collections.emptyList()
        );
    }

    /**
     * <h2>获取模板 ids 到 CouponTemplateSDK 的映射</h2>
     * @param ids 优惠券模板 id
     */
    @Override
    public CommonResponse<Map<Integer, CouponTemplateSDK>>
    findIds2TemplateSDK(Collection<Integer> ids) {

        log.error("[eureka-client-coupon-template] findIds2TemplateSDK" +
                "request error");

        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-template] request error",
                new HashMap<>()
        );
    }
}
