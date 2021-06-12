package com.asan.coupon.dao;

import com.asan.coupon.entity.CouponTemplate;
import com.asan.coupon.mapper.CouponTemplateMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author Asan
 * @date 2021/6/12
 * 优惠券模板Dao
 */

public class CouponTemplateDao {

    @Autowired
    private CouponTemplateMapper couponTemplateMapper;

    /**
     * 根据模板名称查询模板
     * @param name 优惠券模板名称
     * @return
     */
    CouponTemplate findByName(String name) {
        List<CouponTemplate> templates = couponTemplateMapper.selectList(
                new EntityWrapper<CouponTemplate>().eq("name", name));

        return templates.size() > 0 ? templates.get(0) : null;
    }

    /**
     * 根据available和expired 标记查找模板记录
     *  where available =... and expired = ...
     * @param available 是否可用
     * @param expired 是否过期
     * @return
     */
    List<CouponTemplate> findAllByAvailableAndExpired(Boolean available, Boolean expired) {
        List<CouponTemplate> templates = couponTemplateMapper.selectList(
                new EntityWrapper<CouponTemplate>()
                        .eq("available", available)
                        .eq("expired", expired));

        return templates;
    }

    /**
     * 根据expired标记查找模板记录
     * @param expired 是否过期
     * @return
     */
    List<CouponTemplate> findAllByExpired(Boolean expired) {
        return couponTemplateMapper.selectList(new EntityWrapper<CouponTemplate>().eq("expired", expired));
    }
}
