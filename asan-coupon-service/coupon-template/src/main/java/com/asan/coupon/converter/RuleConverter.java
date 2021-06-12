package com.asan.coupon.converter;

import com.alibaba.fastjson.JSON;
import com.asan.coupon.vo.TemplateRule;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Asan
 * @date 2021/6/12
 * 优惠券规则属性转换器
 */
@Converter
public class RuleConverter implements AttributeConverter<TemplateRule, String > {
    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JSON.toJSONString(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        //反序列化 参数1反序列化的参数，参数2反序列化的类型
        return JSON.parseObject(rule, TemplateRule.class);
    }
}
