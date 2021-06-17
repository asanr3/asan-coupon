package com.asan.coupon.dao;

import com.asan.coupon.constant.CouponStatus;
import com.asan.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <h1>Coupon Dao 接口定义</h1>
 * @author Asan
 */
public interface CouponDao extends JpaRepository<Coupon, Integer> {

    /**
     * <h2>根据 userId + 状态寻找优惠券记录</h2>
     * where userId = ... and status = ...
     * */
    List<Coupon> findAllByUserIdAndStatus(Long userId, CouponStatus status);

    /**
     * <h2>根据 userId </h2>
     * */
    List<Coupon> findAllByUserId(Long userId);
}
