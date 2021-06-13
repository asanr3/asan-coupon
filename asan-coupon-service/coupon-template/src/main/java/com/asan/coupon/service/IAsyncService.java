package com.asan.coupon.service;

import com.asan.coupon.entity.CouponTemplate;

/**
 * @author Asan
 * @date 2020/6/13
 * 异步服务接口定义
 */
public interface IAsyncService {

    /** 根据模板异步的创建优惠券码
     * @param template{@link {@link CouponTemplate} 优惠券模板实体
     * */
    void asyncConstructCouponByTemplate(CouponTemplate template);
}
