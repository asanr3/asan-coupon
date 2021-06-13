package com.asan.coupon.service;

import com.asan.coupon.entity.CouponTemplate;
import com.asan.coupon.exception.CouponException;
import com.asan.coupon.vo.TemplateRequest;

/**
 * @author Asan
 * @date 2020/6/13
 * 构建优惠券模板接口定义
 */
public interface IBuildTemplateService {

    /**
     * <h2>创建优惠券模板</h2>
     * @param request {@link TemplateRequest} 模板信息请求对象
     * @return {@link CouponTemplate} 优惠券模板实体
     * */
    CouponTemplate buildTemplate(TemplateRequest request)
            throws CouponException;
}
