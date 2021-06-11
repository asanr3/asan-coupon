package com.asan.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Asan
 * @date 2021/6/11
 * 有效期类型
 */
@Getter
@AllArgsConstructor
public enum PeriodType {

    /**优惠券有效期*/
    REGULAR("固定的（固定的日期）",1),
    SHIFT("变动的（以领取之日开始计算）",2);
    /** 有效期描述*/
    private String description;

    /** 有效期编码*/
    private Integer code;

    public static PeriodType of(Integer code){
        Objects.requireNonNull(code);
        //封装枚举
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                // 返回一个枚举类
                .findAny()
                //如果返回空则会抛出异常
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }
}
