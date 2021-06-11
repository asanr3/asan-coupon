package com.asan.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Asan
 * @date 2021/6/11
 * 分发目标枚举类
 */
@Getter
@AllArgsConstructor
public enum DistributeTarget {

    /** 定义分发目标*/
    SINGLE("单用户",1),
    MULTI("多用户",2);

    /** 分发目标描述*/
    private String description;

    /** 分发目标编码*/
    private Integer code;

    public static DistributeTarget of(Integer code){
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
