package com.asan.coupon.service;

import com.asan.coupon.entity.Coupon;
import com.asan.coupon.exception.CouponException;

import java.util.List;

/**
 * <h1>Redis 相关的操作服务接口定义</h1>
 * 1. 用户的三个状态优惠券 Cache 相关操作
 * 2. 优惠券模板生成的优惠券码 Cache 操作
 * @author Asan
 */
public interface IRedisService {

    /**
     * <h2>根据 userId 和状态找到缓存的优惠券列表数据</h2>
     * @param userId 用户 id
     * @param status 优惠券状态 {@link com.asan.coupon.constant.CouponStatus}
     * @return {@link Coupon}s, 注意, 可能会返回 null, 代表从没有过记录
     * */
    List<Coupon> getCachedCoupons(Long userId, Integer status);

    /**
     * <h2>保存空的优惠券列表到缓存中 (为了防止缓存穿透)</h2>
     * 当在查询用户数据的时候，这些数据本身就不存在，它先去查询redis，redis中没有再去查询数据库这些双重查询就是缓存穿透
     * 所以在用户没有数据的情况下当查询的时候，主动的向redis塞一个空的数据，这样就避免了缓存穿透
     * @param userId 用户 id
     * @param status 优惠券状态列表
     * */
    void saveEmptyCouponListToCache(Long userId, List<Integer> status);

    /**
     * <h2>尝试从 Cache 中获取一个优惠券码</h2>
     * @param templateId 优惠券模板主键
     * @return 优惠券码
     * */
    String tryToAcquireCouponCodeFromCache(Integer templateId);

    /**
     * <h2>将优惠券保存到 Cache 中</h2>
     * @param userId 用户 id
     * @param coupons {@link Coupon}s
     * @param status 优惠券状态
     * @return 保存成功的个数
     * */
    Integer addCouponToCache(Long userId, List<Coupon> coupons,
                             Integer status) throws CouponException;
}
