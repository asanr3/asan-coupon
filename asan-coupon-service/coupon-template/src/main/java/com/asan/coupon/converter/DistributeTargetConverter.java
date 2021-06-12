package com.asan.coupon.converter;

import com.asan.coupon.constant.DistributeTarget;

import javax.persistence.AttributeConverter;

/**
 * @author Asan
 * @date 2021/6/12
 * 分发目标（分发优惠券给哪些用户）枚举属性转换器
 */
public class DistributeTargetConverter implements AttributeConverter<DistributeTarget, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DistributeTarget distributeTarget) {
        return distributeTarget.getCode();
    }

    @Override
    public DistributeTarget convertToEntityAttribute(Integer code) {
        return DistributeTarget.of(code);
    }
}
