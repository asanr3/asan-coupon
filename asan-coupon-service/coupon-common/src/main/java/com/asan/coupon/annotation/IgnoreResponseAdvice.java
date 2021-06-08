package com.asan.coupon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Asan
 * 忽略统一响应注解定义
 * 由于我们编写了统一的响应格式，如果我们某些类方法或者接口不希望使用统一的响应格式则可以使用该注解标识不使用统一响应
 * @Target 标识注解可以作用在什么地方，下面指定类和方法
 * @Retention 什么时候起作用， 下面给定 运行时起作用
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
