package com.asan.coupon.service;

import com.asan.coupon.entity.CouponTemplate;
import com.asan.coupon.exception.CouponException;
import com.asan.coupon.vo.CouponTemplateSDK;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Asan
 * @date 2020/6/13
 * 优惠券模板基础（view， delete..）服务定义
 */
public interface ITemplateBaseService {

    /**
     * 根据优惠券模板 id 获取优惠券模板信息
     * @param id 模板id
     * @return {@link CouponTemplate} 优惠券模板实体
     * */
    CouponTemplate buildTemplateInfo(Integer id) throws CouponException;

    /**
     * 查找所有可用的优惠券模板
     * @return {@link CouponTemplateSDK}S
     * */
    List<CouponTemplateSDK> findAllUsableTemplate();

    /**
     * 获取模板 ids 到CouponTemplateSDK的映射
     * @param ids 模板 ids
     * @return Map<key:模板id，value: CouponTemplateSDK>
     * */
    Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(Collection<Integer> ids);
}
