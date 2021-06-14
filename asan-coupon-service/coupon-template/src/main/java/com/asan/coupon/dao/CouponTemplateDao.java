package com.asan.coupon.dao;

import com.asan.coupon.entity.CouponTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Asan
 * @date 2021/6/12
 * CouponTemplate Dao接口定义
 * JpaRepository<T, ID> T:是实体类的定义（类型）  ID是实体类主键的类型
 */
public interface CouponTemplateDao extends JpaRepository<CouponTemplate, Integer> {

    /**
     * 根据模板名称查询模板
     * @param name 优惠券模板名称
     * @return 优惠券信息
     */
    CouponTemplate findByName(String name);

    /**
     * 根据available和expired 标记查找模板记录
     *    where available =... and expired = ...
     * @param available 是否可用
     * @param expired 是否过期
     * @return 优惠券模板信息集合
     */
    List<CouponTemplate> findAllByAvailableAndExpired(
            Boolean available, Boolean expired
    );

    /**
     * 根据expired标记查找模板记录
     * */
    List<CouponTemplate> findAllByExpired(Boolean expired);
}
