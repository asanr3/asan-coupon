package com.asan.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Asan
 * @date 2021/6/11
 * 优惠券分类枚举类
 */
@Getter
@AllArgsConstructor
public enum CouponCategory {
    /**满减券*/
    MANJIAN("满减券","001"),
    /**折扣券*/
    ZHEKOU("折扣券","002"),
    /**立减券*/
    LIJIAN("立减券","003");

    /** 优惠券描述（分类）*/
    private String description;

    /** 优惠券分类编码*/
    private String code;

    public static CouponCategory of(String code){
        Objects.requireNonNull(code);
                //封装枚举
        return Stream.of(values())
                //过滤枚举
                .filter(bean -> bean.code.equals(code))
                //返回一个枚举类
                .findAny()
                //如果返回空则会抛出异常
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }
}
