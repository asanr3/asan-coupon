package com.asan.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Asan
 * @date 2021/6/11
 * 产品线枚举类
 */
@Getter
@AllArgsConstructor
public enum  ProductLine {
    /**定义两个产品线*/
    DAMAO("大猫",1),
    DABAO("大宝",2);

    /** 产品线描述*/
    private String description;

    /** 产品线编码*/
    private Integer code;

    public static ProductLine of(Integer code){
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
